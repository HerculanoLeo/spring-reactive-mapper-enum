package com.herculanoleo.spring.reactive.me.service;

import com.herculanoleo.spring.reactive.me.models.dto.PersonRequest;
import com.herculanoleo.spring.reactive.me.models.dto.PersonResponse;
import com.herculanoleo.spring.reactive.me.models.dto.PersonSearchRequest;
import com.herculanoleo.spring.reactive.me.models.enums.PersonStatus;
import com.herculanoleo.spring.reactive.me.persistence.entity.Person;
import com.herculanoleo.spring.reactive.me.persistence.entity.QPerson;
import com.herculanoleo.spring.reactive.me.persistence.predicates.PersonPredicates;
import com.herculanoleo.spring.reactive.me.persistence.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public Flux<PersonResponse> findAll(PersonSearchRequest requestEntity) {
        var person = QPerson.person;
        return repository
                .query(query -> query.select(repository.entityProjection())
                        .from(person)
                        .where(PersonPredicates
                                .likeName(requestEntity.getName())
                                .and(PersonPredicates.birthdateGreaterThanOrEqualTo(requestEntity.getBirthdateFrom()))
                                .and(PersonPredicates.birthdateLessThanOrEqualTo(requestEntity.getBirthdateTo()))
                                .and(PersonPredicates.status(requestEntity.getStatus())))
                        .orderBy(person.name.asc(), person.birthdate.desc()))
                .all()
                .map(this::map);
    }

    public Mono<PersonResponse> findById(Long id) {
        return repository.findById(id).map(this::map);
    }

    public Mono<PersonResponse> register(PersonRequest requestEntity) {
        return repository.save(Person.builder()
                        .name(requestEntity.name())
                        .birthdate(requestEntity.birthdate())
                        .status(PersonStatus.ACTIVE)
                        .build())
                .map(this::map);
    }

    public Mono<PersonResponse> update(Long id, PersonRequest requestEntity) {
        return this.repository.findById(id)
                .flatMap(entity -> {
                    entity.setName(requestEntity.name());
                    entity.setBirthdate(requestEntity.birthdate());
                    return this.repository.save(entity);
                })
                .map(this::map);
    }

    public Mono<Void> active(Long id) {
        return repository.findById(id)
                .flatMap(entity -> {
                    if (Objects.equals(PersonStatus.ACTIVE, entity.getStatus())) {
                        return Mono.error(new RuntimeException("cannot activate person that already active"));
                    }
                    entity.setStatus(PersonStatus.ACTIVE);
                    return this.repository.save(entity);
                })
                .then();
    }

    public Mono<Void> inactive(Long id) {
        return repository.findById(id)
                .flatMap(entity -> {
                    if (Objects.equals(PersonStatus.INACTIVE, entity.getStatus())) {
                        return Mono.error(new RuntimeException("cannot activate person that already active"));
                    }
                    entity.setStatus(PersonStatus.INACTIVE);
                    return this.repository.save(entity);
                })
                .then();
    }

    private PersonResponse map(Person entityToMap) {
        return new PersonResponse(entityToMap.getId(), entityToMap.getName(),
                entityToMap.getBirthdate(), entityToMap.getStatus());
    }
}
