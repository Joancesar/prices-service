package com.inditex.pricesservice.controller;

import com.inditex.pricesservice.persistence.Price;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PricesControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest
    @MethodSource("provideParams")
    void shouldGetPricesByAppliedDateAndProductIdAndId(String appliedDate, String productId,
                                                       String brandId, Long expectedPriceId
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("appliedDate", appliedDate);
        urlParams.put("productId", productId);
        urlParams.put("brandId", brandId);

        String url = "/api/v1/prices" +
            "?appliedDate={appliedDate}&productId={productId}&brandId={brandId}";

        ResponseEntity<Price> actualPrice = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Price.class,
                urlParams
        );

        assertEquals(HttpStatus.OK, actualPrice.getStatusCode());
        assertNotNull(actualPrice.getBody());
        assertEquals(expectedPriceId, actualPrice.getBody().getPriceList());
    }

    static Stream<Arguments> provideParams() {
        return Stream.of(
                Arguments.of("2020-06-14 10:00:00", "35455", "1", 1L),
                Arguments.of("2020-06-14 16:00:00", "35455", "1", 2L),
                Arguments.of("2020-06-14 21:00:00", "35455", "1", 1L),
                Arguments.of("2020-06-15 10:00:00", "35455", "1", 3L),
                Arguments.of("2020-06-16 21:00:00", "35455", "1", 4L)
        );
    }

    @Test
    void shouldReturnNotFound() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("appliedDate", "2023-03-29 21:00:00");
        urlParams.put("productId", "99999");
        urlParams.put("brandId", "9");

        String url = "/api/v1/prices" +
                "?appliedDate={appliedDate}&productId={productId}&brandId={brandId}";

        ResponseEntity<Price> actualPrice = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Price.class,
                urlParams
        );

        assertEquals(HttpStatus.NOT_FOUND, actualPrice.getStatusCode());
        assertNull(actualPrice.getBody());
    }
}
