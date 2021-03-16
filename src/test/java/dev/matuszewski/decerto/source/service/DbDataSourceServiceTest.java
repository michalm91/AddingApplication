package dev.matuszewski.decerto.source.service;

import dev.matuszewski.decerto.source.model.RandomNumber;
import dev.matuszewski.decerto.source.reposotory.RandomNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DbDataSourceServiceTest {
    @Mock
    private RandomNumberRepository randomNumberRepository;
    @InjectMocks
    private DbDataSourceService dbDataSourceService;

    private static final Long EXAMPLE_VALUE = 10292012L;

    @Test
    public void should_returnDbValue_when_dbIsFilled() {
        //GIVEN
        when(randomNumberRepository.findAll()).thenReturn(getExampleRandomNumber());

        //WHEN
        final BigDecimal result = dbDataSourceService.obtainData();

        //THEN
        assertEquals(BigDecimal.valueOf(EXAMPLE_VALUE), result);
    }

    private List<RandomNumber> getExampleRandomNumber() {
        return List.of(
                RandomNumber.builder().number(EXAMPLE_VALUE).build()
        );
    }
}
