package dev.matuszewski.decerto.source.service;

import dev.matuszewski.decerto.Constants;
import dev.matuszewski.decerto.source.exception.RandomApiInvalidResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiDataSourceServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ApiDataSourceService apiDataSourceService;

    private static final String EXAMPLE_VALUE = "10292012";
    private static final String EXAMPLE_INVALID_VALUE = "10df201as211";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(apiDataSourceService, "URL", "https://www.random.org");
    }

    @Test
    public void should_returnApiValue_when_apiIsAvailable() {
        //GIVEN
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(String.class),
                eq(Constants.MIN_VALUE),
                eq(Constants.MAX_VALUE)
        )).thenReturn(ResponseEntity.ok(getExampleResponseRandomNumber()));

        //WHEN
        final BigDecimal result = apiDataSourceService.obtainData();

        //THEN
        assertEquals(new BigDecimal(EXAMPLE_VALUE), result);
    }

    @Test
    public void should_throwException_when_apiIsUnavailable() {
        //GIVEN
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(String.class),
                eq(Constants.MIN_VALUE),
                eq(Constants.MAX_VALUE)
        )).thenReturn(ResponseEntity.notFound().build());

        //THEN
        assertThrows(RandomApiInvalidResponse.class, () -> apiDataSourceService.obtainData());
    }

    @Test
    public void should_returnIncorrectApiValue_when_apiIsChanged() {
        //GIVEN
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(String.class),
                eq(Constants.MIN_VALUE),
                eq(Constants.MAX_VALUE)
        )).thenReturn(ResponseEntity.ok(getExampleInvalidResponseRandomNumber()));

        //THEN
        assertThrows(RandomApiInvalidResponse.class, () -> apiDataSourceService.obtainData());
    }

    private String getExampleResponseRandomNumber() {
        return EXAMPLE_VALUE;
    }

    private String getExampleInvalidResponseRandomNumber() {
        return EXAMPLE_INVALID_VALUE;
    }
}
