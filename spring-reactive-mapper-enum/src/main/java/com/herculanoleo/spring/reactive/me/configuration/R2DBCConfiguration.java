package com.herculanoleo.spring.reactive.me.configuration;

import com.herculanoleo.spring.reactive.me.models.enums.MapperEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ConverterBuilder;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.ArrayList;
import java.util.function.Function;

public class R2DBCConfiguration {

    private final DatabaseClient databaseClient;

    private final MapperResourceLoader resourceLoader;

    public R2DBCConfiguration(DatabaseClient databaseClient, MapperResourceLoader resourceLoader) {
        this.databaseClient = databaseClient;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public R2dbcCustomConversions r2dbcCustomConversions() {
        var classes = resourceLoader.getClasses();

        var dialect = DialectResolver.getDialect(this.databaseClient.getConnectionFactory());
        var converters = new ArrayList<GenericConverter>(classes.size() * 2);

        for (var clazz : classes) {
            var mappersConverters = ConverterBuilder
                    .reading(
                            String.class,
                            (Class<MapperEnum>) clazz,
                            MapperConverterBuilder.build(clazz)
                    )
                    .andWriting(MapperEnum::getValue)
                    .getConverters();
            converters.addAll(mappersConverters);
        }

        return R2dbcCustomConversions.of(dialect, converters);
    }

    protected static class MapperConverterBuilder {
        private MapperConverterBuilder() {
        }

        public static <T extends MapperEnum> Function<String, T> build(final Class<T> clazz) {
            return (source) -> MapperEnum.fromValue(source, clazz);
        }
    }
}
