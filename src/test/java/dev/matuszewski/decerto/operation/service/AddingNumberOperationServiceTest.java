package dev.matuszewski.decerto.operation.service;

import dev.matuszewski.decerto.operation.model.Result;
import dev.matuszewski.decerto.operation.reposotory.ResultRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AddingNumberOperationServiceTest {
    @Autowired
    private AddingNumberOperationService addingNumberOperationService;
    @Autowired
    private ResultRepository resultRepository;

    private static final String EXAMPLE_DB_LAST_VALUE = "10";
    private static final String EXAMPLE_CURRENT_VALUE = "5";
    private static final String EXPECTED_SUM_VALUE = "15";
    private static final String INVALID_CURRENT_VALUE = "1aa5";

    @Test
    public void should_addValue_when_lastValueExist() {
        //GIVEN
        resultRepository.save(getExampleResult());

        //WHEN
        addingNumberOperationService.process(EXAMPLE_CURRENT_VALUE);
        final String result = retrieveResultValue(resultRepository.findTopByOrderByIdDesc());

        //THEN
        assertEquals(EXPECTED_SUM_VALUE, result);
    }

    @Test
    public void should_addValue_when_lastValueNonExist() {
        //GIVEN
        resultRepository.deleteAll();

        //WHEN
        addingNumberOperationService.process(EXAMPLE_CURRENT_VALUE);
        final String result = retrieveResultValue(resultRepository.findTopByOrderByIdDesc());

        //THEN
        assertEquals(EXAMPLE_CURRENT_VALUE, result);
    }

    @Test
    public void should_throwException_when_currentValueHasCharacter() {
        //THEN
        assertThrows(UnsupportedOperationException.class, () -> addingNumberOperationService.process(INVALID_CURRENT_VALUE));
    }

    private Result getExampleResult() {
        return Result.builder()
                .lastValue(EXAMPLE_DB_LAST_VALUE)
                .result(EXAMPLE_DB_LAST_VALUE)
                .build();
    }

    private String retrieveResultValue(final Optional<Result> result) {
        return result
                .map(Result::getResult)
                .orElse(BigDecimal.ZERO.toString());
    }
}
