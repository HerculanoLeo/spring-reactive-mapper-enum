package com.herculanoleo.spring.reactive.me.persistence.entity;

import com.herculanoleo.spring.reactive.me.models.enums.PersonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "person")
public class Person {

    @Id
    private Long id;

    private String name;

    private LocalDate birthdate;

    private PersonStatus status;

}
