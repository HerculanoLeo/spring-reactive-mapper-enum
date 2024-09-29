package com.herculanoleo.spring.reactive.me.utils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public class PredicateUtils {
    private PredicateUtils() {
    }

    public static BooleanExpression asTrue() {
        return Expressions.asBoolean(true).isTrue();
    }
}
