package dev.matuszewski.decerto.operation.service;

import dev.matuszewski.decerto.Constants;
import dev.matuszewski.decerto.LogMessages;
import dev.matuszewski.decerto.operation.dto.ResultDto;
import dev.matuszewski.decerto.operation.mapper.ResultMapper;
import dev.matuszewski.decerto.operation.reposotory.ResultRepository;
import dev.matuszewski.decerto.source.service.IDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultMapper resultMapper;
    private final ResultRepository resultRepository;
    private final ApplicationContext applicationContext;
    private final AddingNumberOperationService addingNumberOperationService;

    public ResultDto receiveCurrentResult() {
        return resultRepository.findTopByOrderByIdDesc()
                .map(resultMapper::modelToDto)
                .orElse(ResultDto.builder()
                        .lastValue(BigDecimal.ZERO.toString())
                        .currentValue(BigDecimal.ZERO.toString())
                        .build());
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 2000)
    public void work() {
        final List<IDataSource> sources = receiveAllSource();
        sources.forEach(iDataSource -> {
            try {
                log.info(String.format(LogMessages.REQUEST_FOR_ADDING, iDataSource.getClass().getSimpleName()));
                addingNumberOperationService.process(iDataSource.obtainData().toString());
            } catch (final Exception exception) {
                log.error(String.format(LogMessages.CANNOT_RECEIVE_DATA, iDataSource.getClass().getSimpleName(), exception.getMessage()));
            }
        });
    }

    private List<IDataSource> receiveAllSource() {
        return Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(beanName -> beanName.startsWith(Constants.OPERATION_BEAN_PREFIX))
                .map(beanName -> (IDataSource) applicationContext.getBean(beanName))
                .collect(Collectors.toUnmodifiableList());
    }
}
