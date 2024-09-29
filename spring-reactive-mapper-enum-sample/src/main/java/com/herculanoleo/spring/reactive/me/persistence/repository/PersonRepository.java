package com.herculanoleo.spring.reactive.me.persistence.repository;

import com.herculanoleo.spring.reactive.me.persistence.entity.Person;
import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;

public interface PersonRepository extends QuerydslR2dbcRepository<Person, Long> {
}
