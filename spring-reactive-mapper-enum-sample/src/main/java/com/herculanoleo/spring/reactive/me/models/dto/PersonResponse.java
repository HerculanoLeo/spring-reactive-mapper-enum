package com.herculanoleo.spring.reactive.me.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.herculanoleo.spring.reactive.me.models.enums.PersonStatus;

import java.time.LocalDate;

public record PersonResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("name")
        String name,
        @JsonProperty("birthdate")
        LocalDate birthdate,
        @JsonProperty("status")
        PersonStatus status
) {
}
