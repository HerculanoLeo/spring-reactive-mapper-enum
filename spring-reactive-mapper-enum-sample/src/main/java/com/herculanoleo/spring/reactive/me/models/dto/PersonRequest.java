package com.herculanoleo.spring.reactive.me.models.dto;

import java.time.LocalDate;

public record PersonRequest(
        String name,
        LocalDate birthdate
) {
}
