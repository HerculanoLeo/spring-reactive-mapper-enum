package com.herculanoleo.spring.reactive.me.controller;

import com.herculanoleo.spring.reactive.me.models.dto.PersonRequest;
import com.herculanoleo.spring.reactive.me.models.dto.PersonSearchRequest;
import com.herculanoleo.spring.reactive.me.models.enums.PersonStatus;
import com.herculanoleo.spring.reactive.me.persistence.entity.Person;
import com.herculanoleo.spring.reactive.me.persistence.repository.PersonRepository;
import com.herculanoleo.spring.reactive.me.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.r2dbc.core.RowsFetchSpec;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PersonControllerE2ETest {

    @MockBean
    PersonRepository personRepository;

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    @Autowired
    private PersonService personService;

    @Test
    @SuppressWarnings("unchecked")
    void findAllTest() {
        var entity = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build();

        var rowMock = Mockito.mock(PersonRowsFetchSpec.class);
        Mockito.when(personRepository.query(any(Function.class))).thenReturn(rowMock);
        Mockito.when(rowMock.all()).thenReturn(Flux.just(entity));

        webTestClient.get()
                .uri("/person?name=%s&birthdateFrom=%s&birthdateTo=%s&status=%s".formatted(
                        "John",
                        "2000-01-01",
                        "2000-12-31",
                        "A"
                ))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(
                        """
                                        [
                                            {
                                                "id": 1,
                                                "name": "John Doe",
                                                "birthdate": "2000-01-01",
                                                "status": "A"
                                            }
                                        ]
                                """
                );

        Mockito.verify(personService).findAll(eq(PersonSearchRequest.builder()
                .name("John")
                .birthdateFrom(LocalDate.of(2000, 1, 1))
                .birthdateTo(LocalDate.of(2000, 12, 31))
                .status(PersonStatus.ACTIVE)
                .build()));
    }

    @Test
    void findByIdTest() {
        var entity = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build();

        Mockito.when(personRepository.findById(eq(1L))).thenReturn(Mono.just(entity));

        webTestClient.get()
                .uri("/person/{id}", 1L)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("""
                            {
                                "id": 1,
                                "name": "John Doe",
                                "birthdate": "2000-01-01",
                                "status": "A"
                            }
                        """);
    }

    @Test
    void registerTest() {
        var entity = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build();

        var requestEntity = new PersonRequest("John Doe", LocalDate.of(2000, 1, 1));

        Mockito.when(personRepository.save(eq(Person.builder()
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build()))).thenReturn(Mono.just(entity));

        webTestClient.post()
                .uri("/person")
                .bodyValue(requestEntity)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json("""
                            {
                                "id": 1,
                                "name": "John Doe",
                                "birthdate": "2000-01-01",
                                "status": "A"
                            }
                        """
                );
    }

    @Test
    void updateTest() {
        var requestEntity = new PersonRequest("John Doe - Edit", LocalDate.of(2000, 1, 1));

        var entityBeforeSave = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build();
        Mockito.when(personRepository.findById(eq(1L))).thenReturn(Mono.just(entityBeforeSave));

        var entityAfterSave = Person.builder()
                .id(1L)
                .name("John Doe - Edit")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build();

        Mockito.when(personRepository.save(eq(entityAfterSave))).thenReturn(Mono.just(entityAfterSave));

        webTestClient.put()
                .uri("/person/{id}", 1L)
                .bodyValue(requestEntity)
                .exchange()
                .expectStatus()
                .isAccepted()
                .expectBody()
                .json("""
                            {
                               "id": 1,
                               "name": "John Doe - Edit",
                               "birthdate": "2000-01-01",
                               "status": "A"
                           }
                        """);
    }

    @Test
    void activeTest() {
        var entity = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.INACTIVE)
                .build();
        Mockito.when(personRepository.findById(eq(1L))).thenReturn(Mono.just(entity));

        var entityResult = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build();
        Mockito.when(personRepository.save(eq(entityResult))).thenReturn(Mono.just(entityResult));

        webTestClient.put()
                .uri("/person/{id}/active", 1L)
                .exchange()
                .expectStatus()
                .isAccepted();

        Mockito.verify(personRepository).save(eq(entityResult));
    }

    @Test
    void activeErrorTest() {
        var entity = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build();
        Mockito.when(personRepository.findById(eq(1L))).thenReturn(Mono.just(entity));

        webTestClient.put()
                .uri("/person/{id}/active", 1L)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    void inactiveTest() {
        var entity = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.ACTIVE)
                .build();
        Mockito.when(personRepository.findById(eq(1L))).thenReturn(Mono.just(entity));

        var entityResult = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.INACTIVE)
                .build();
        Mockito.when(personRepository.save(eq(entityResult))).thenReturn(Mono.just(entityResult));

        webTestClient.delete()
                .uri("/person/{id}/inactive", 1L)
                .exchange()
                .expectStatus()
                .isAccepted();

        Mockito.verify(personRepository).save(eq(entityResult));
    }

    @Test
    void inactiveErrorTest() {
        var entity = Person.builder()
                .id(1L)
                .name("John Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .status(PersonStatus.INACTIVE)
                .build();
        Mockito.when(personRepository.findById(eq(1L))).thenReturn(Mono.just(entity));

        webTestClient.delete()
                .uri("/person/{id}/inactive", 1L)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    private interface PersonRowsFetchSpec extends RowsFetchSpec<Person> {
    }
}
