package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.config.TestContainersMongoConfig;

import com.jumbo.store.geo.controller.dto.StoresResult;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContainersMongoConfig.Initializer.class)
@ActiveProfiles("test") // Uses the "test" profile for the test configuration
class StoreControllerITest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        storeRepository.deleteAll();
        // Insert test data
        initialization();
    }

    @Test
    void getNearestStores() {
        ResponseEntity<StoresResult> response = testRestTemplate.getForEntity(
                "/api/v1/nearest-stores?latitude=52.37867&longitude=4.883832", StoresResult.class);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    private void initialization() {
        Store store1 = Store.builder()
                .city("Amsterdam")
                .postalCode("1012AB")
                .street("Damstraat")
                .longitude(4.895168)
                .latitude(52.370216)
                .build();

        Store store2 = Store.builder()
                .city("Rotterdam")
                .postalCode("3011AA")
                .street("Coolsingel")
                .longitude(4.47917)
                .latitude(51.9225)
                .build();

        storeRepository.save(store1);
        storeRepository.save(store2);
    }
}