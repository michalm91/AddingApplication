package dev.matuszewski.decerto.operation.service;

import dev.matuszewski.decerto.Constants;
import dev.matuszewski.decerto.LogMessages;
import dev.matuszewski.decerto.operation.model.Result;
import dev.matuszewski.decerto.operation.reposotory.ResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddingNumberOperationService implements IOperation {

    private final ResultRepository resultRepository;

    @Override
    public synchronized void process(final String value) {
        resultRepository.findTopByOrderByIdDesc()
                .ifPresentOrElse(processExistRecord(value), processNotExistRecord(value));
    }

    private String numberAdding(final String value) {
        return numberAdding(value, BigDecimal.ZERO.toString());
    }

    private String numberAdding(final String value, final String current) {
        if (!value.matches(Constants.ONLY_NUMBERS_REGEX) || !current.matches(Constants.ONLY_NUMBERS_REGEX)) {
            throw new UnsupportedOperationException();
        }

        final BigDecimal one = new BigDecimal(value);
        final BigDecimal two = new BigDecimal(current);
        final BigDecimal result = one.add(two);
        log.info(String.format(LogMessages.RESULT_ADDING_OPERATION, one, two, result));

        return result.toString();
    }

    private Consumer<Result> processExistRecord(final String value) {
        return dbResult -> {
            final String operationResult = numberAdding(value, dbResult.getResult());
            dbResult.setLastValue(value);
            dbResult.setResult(operationResult);
            resultRepository.save(dbResult);
        };
    }

    private Runnable processNotExistRecord(final String value) {
        return () -> {
            final String operationResult = numberAdding(value);
            final Result dbResult = Result.builder()
                    .lastValue(value)
                    .result(operationResult)
                    .build();

            resultRepository.save(dbResult);
        };
    }
}
