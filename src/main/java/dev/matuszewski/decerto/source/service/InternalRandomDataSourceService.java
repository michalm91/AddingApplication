package dev.matuszewski.decerto.source.service;

import dev.matuszewski.decerto.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service("work.internalRandomDataSourceService")
@RequiredArgsConstructor
public class InternalRandomDataSourceService implements IDataSource {

    @Override
    public BigDecimal obtainData() {
        final Random random = new Random();
        return BigDecimal.valueOf(random.nextInt(Constants.MAX_VALUE - Constants.MIN_VALUE) + Constants.MIN_VALUE);
    }
}
