package dev.matuszewski.decerto.source.service;

import dev.matuszewski.decerto.source.model.RandomNumber;
import dev.matuszewski.decerto.source.reposotory.RandomNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service("work.dbDataSourceService")
@RequiredArgsConstructor
public class DbDataSourceService implements IDataSource {
    private final RandomNumberRepository randomNumberRepository;

    @Override
    public BigDecimal obtainData() {
        final Random random = new Random();
        final List<RandomNumber> randomNumbers = randomNumberRepository.findAll();

        return randomNumbers.stream()
                .skip(random.nextInt(randomNumbers.size()))
                .findFirst()
                .map(randomNumber -> BigDecimal.valueOf(randomNumber.getNumber()))
                .orElse(BigDecimal.ZERO);
    }
}
