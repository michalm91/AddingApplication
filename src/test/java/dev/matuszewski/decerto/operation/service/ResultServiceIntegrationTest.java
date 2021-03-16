package dev.matuszewski.decerto.operation.service;

import dev.matuszewski.decerto.operation.dto.ResultDto;
import dev.matuszewski.decerto.operation.model.Result;
import dev.matuszewski.decerto.operation.reposotory.ResultRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResultServiceIntegrationTest {

    @Autowired
    private ResultService resultService;
    @Autowired
    private ResultRepository resultRepository;

    private static final String EXAMPLE_DB_LAST_VALUE = "18";
    private static final String EXAMPLE_CURRENT_VALUE = "2";

    @Test
    public void should_returnValue_when_allSourceAvailable() {
        //GIVEN
        resultRepository.deleteAll();

        //WHEN
        resultService.work();
        final Result result = resultRepository.findTopByOrderByIdDesc()
                .orElse(null);

        //THEN
        assertNotNull(result);
        assertTrue(result.getResult().matches("[0-9]+"));
        assertTrue(result.getLastValue().matches("[0-9]+"));
    }

    @Test
    public void should_returnValue_when_lastResultExist() {
        //GIVEN
        resultRepository.save(getExampleResult());

        //WHEN
        final ResultDto result = resultService.receiveCurrentResult();

        //THEN
        assertEquals(EXAMPLE_DB_LAST_VALUE, result.getLastValue());
        assertEquals(EXAMPLE_CURRENT_VALUE, result.getCurrentValue());
    }

    @Test
    public void should_returnZeroValue_when_lastResultNonExist() {
        //GIVEN
        resultRepository.deleteAll();

        //WHEN
        final ResultDto result = resultService.receiveCurrentResult();

        //THEN
        assertEquals(BigDecimal.ZERO.toString(), result.getLastValue());
        assertEquals(BigDecimal.ZERO.toString(), result.getCurrentValue());
    }

    private Result getExampleResult() {
        return Result.builder()
                .lastValue(EXAMPLE_DB_LAST_VALUE)
                .result(EXAMPLE_CURRENT_VALUE)
                .build();
    }
}
