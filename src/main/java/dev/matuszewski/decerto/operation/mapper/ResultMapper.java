package dev.matuszewski.decerto.operation.mapper;

import dev.matuszewski.decerto.operation.dto.ResultDto;
import dev.matuszewski.decerto.operation.model.Result;
import org.springframework.stereotype.Component;

@Component
public class ResultMapper {

    public ResultDto modelToDto(final Result result) {
        if (result == null) {
            return ResultDto.builder().build();
        }

        return ResultDto.builder()
                .lastValue(result.getLastValue())
                .currentValue(result.getResult())
                .build();
    }
}
