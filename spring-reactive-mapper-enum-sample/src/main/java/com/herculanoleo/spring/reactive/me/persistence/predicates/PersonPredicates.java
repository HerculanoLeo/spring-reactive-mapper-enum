package com.herculanoleo.spring.reactive.me.persistence.predicates;

import com.herculanoleo.spring.reactive.me.models.enums.PersonStatus;
import com.herculanoleo.spring.reactive.me.persistence.entity.QPerson;
import com.herculanoleo.spring.reactive.me.utils.PredicateUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@UtilityClass
public class PersonPredicates {

    public static BooleanExpression likeName(final String name) {
        if (StringUtils.isNotBlank(name)) {
            return QPerson.person.name.upper().like("%%%s%%".formatted(name.toUpperCase()));
        }
        return PredicateUtils.asTrue();
    }

    public static BooleanExpression birthdateGreaterThanOrEqualTo(final LocalDate date) {
        if (null != date) {
            return QPerson.person.birthdate.goe(date);
        }
        return PredicateUtils.asTrue();
    }

    public static BooleanExpression birthdateLessThanOrEqualTo(final LocalDate date) {
        if (null != date) {
            return QPerson.person.birthdate.loe(date);
        }
        return PredicateUtils.asTrue();
    }

    public static BooleanExpression status(final PersonStatus status) {
        if (null != status) {
            return QPerson.person.status.stringValue().eq(status.getValue());
        }
        return PredicateUtils.asTrue();
    }

}
