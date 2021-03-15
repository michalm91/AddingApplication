package dev.matuszewski.decerto.source.service;

import dev.matuszewski.decerto.Constants;
import dev.matuszewski.decerto.source.exception.RandomApiInvalidResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service("work.apiDataSourceService")
@RequiredArgsConstructor
public class ApiDataSourceService implements IDataSource {
    private final RestTemplate restTemplate;

    private static final String NUMBER_URI = "/integers/?num=1&min={MIN}&max={MAX}&col=1&base=10&format=plain&rnd=new";

    @Value("${decerto.api.random}")
    private String URL;

    @Override
    public BigDecimal obtainData() {
        final ResponseEntity<String> response = receiveValueFromApi();
        return Optional.of(response)
                .filter(responseEntity -> response.getStatusCode().is2xxSuccessful())
                .map(this::prepareResponse)
                .filter(s -> s.matches(Constants.ONLY_NUMBERS_REGEX))
                .map(BigDecimal::new)
                .orElseThrow(RandomApiInvalidResponse::new);
    }

    private ResponseEntity<String> receiveValueFromApi() {
        return restTemplate.exchange(
                StringUtils.join(URL, NUMBER_URI),
                HttpMethod.GET,
                null,
                String.class,
                Constants.MIN_VALUE,
                Constants.MAX_VALUE
        );
    }

    private String prepareResponse(final ResponseEntity<String> responseEntity) {
        return Optional.of(responseEntity)
                .map(response -> Objects.requireNonNull(response.getBody()).trim().replaceAll(StringUtils.LF, StringUtils.EMPTY))
                .orElse(StringUtils.EMPTY);
    }
}
