package com.herculanoleo.spring.reactive.me.controller;

import com.herculanoleo.spring.reactive.me.models.dto.PersonRequest;
import com.herculanoleo.spring.reactive.me.models.dto.PersonResponse;
import com.herculanoleo.spring.reactive.me.models.dto.PersonSearchRequest;
import com.herculanoleo.spring.reactive.me.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping
    public ResponseEntity<Flux<PersonResponse>> findAll(PersonSearchRequest requestEntity) {
        return ResponseEntity.ok(service.findAll(requestEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<Mono<PersonResponse>> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Mono<PersonResponse>> register(@RequestBody PersonRequest requestEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(requestEntity));
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<PersonResponse>> update(@PathVariable("id") Long id, @RequestBody PersonRequest requestEntity) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.update(id, requestEntity));
    }

    @PutMapping("{id}/active")
    public ResponseEntity<Mono<Void>> active(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(service.active(id));
    }

    @DeleteMapping("{id}/inactive")
    public ResponseEntity<Mono<Void>> inactive(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(service.inactive(id));
    }

}
