package com.herculanoleo.spring.reactive.me.models.dto;

import com.herculanoleo.spring.reactive.me.models.enums.PersonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonSearchRequest {

    private String name;

    private LocalDate birthdateFrom;

    private LocalDate birthdateTo;

    private PersonStatus status;

}
