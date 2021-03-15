package dev.matuszewski.decerto.service.source;

import dev.matuszewski.decerto.source.service.InternalRandomDataSourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class InternalRandomDataSourceServiceTest {
    private InternalRandomDataSourceService internalRandomDataSourceService;

    @BeforeEach
    public void setUp() {
        internalRandomDataSourceService = new InternalRandomDataSourceService();
    }

    @Test
    public void should_returnAnyValue_when_randomWorksCorrect() {
        //WHEN
        final BigDecimal result = internalRandomDataSourceService.obtainData();

        //THEN
        assertTrue(result.toString().matches("[0-9]+"));
    }
}
