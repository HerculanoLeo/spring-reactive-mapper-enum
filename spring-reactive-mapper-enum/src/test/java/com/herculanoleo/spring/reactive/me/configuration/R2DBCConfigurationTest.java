package com.herculanoleo.spring.reactive.me.configuration;

import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mocks.StatusMockEnum;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class R2DBCConfigurationTest {

    @Mock
    private DatabaseClient databaseClient;

    @Mock
    private MapperResourceLoader resourceLoader;

    @Spy
    @InjectMocks
    private R2DBCConfiguration configuration;

    @DisplayName("Should return the custom converters")
    @Test
    public void r2dbcCustomConversionsTest() {
        when(this.resourceLoader.getClasses()).thenReturn(List.of(StatusMockEnum.class));

        var connectionFactoryMetadataMock = mock(ConnectionFactoryMetadata.class);
        when(connectionFactoryMetadataMock.getName()).thenReturn("H2");

        var connectionFactoryMock = mock(ConnectionFactory.class);
        when(connectionFactoryMock.getMetadata()).thenReturn(connectionFactoryMetadataMock);

        when(this.databaseClient.getConnectionFactory()).thenReturn(connectionFactoryMock);

        var conversions = this.configuration.r2dbcCustomConversions();

        assertTrue(conversions.hasCustomWriteTarget(StatusMockEnum.class, String.class));
        assertTrue(conversions.hasCustomReadTarget(String.class, StatusMockEnum.class));
    }

    @DisplayName("Should build and convert a String value to SerializableEnum value")
    @Test
    public void converterBuilderTest() {
        var converterFun = R2DBCConfiguration.MapperConverterBuilder.build(StatusMockEnum.class);
        assertEquals(StatusMockEnum.ACTIVE, converterFun.apply(StatusMockEnum.ACTIVE.getValue()));
    }

}
