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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

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

    // result is Latitude must be between -90 and 90 degrees.
    @Test
    void getNearestStores_withInvalidLatitude() {
        // Invalid latitude
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                "/api/v1/nearest-stores?latitude=95.0&longitude=4.883832", String.class);
                Assertions.assertThat(response.getBody()).contains("Latitude must be between -90 and 90 degrees.");
    }

    //Longitude must be between -180 and 180 degrees
    @Test
    void getNearestStores_withInvalidLongitude() {
        // Invalid longitude
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                "/api/v1/nearest-stores?latitude=52.37867&longitude=185.0", String.class);
                Assertions.assertThat(response.getBody()).contains("Longitude must be between -180 and 180 degrees.");
    }

        //Latitude is required
        @Test
        void getNearestStores_withMissingLatitude() {
            // Missing latitude
            ResponseEntity<String> response = testRestTemplate.getForEntity(
                    "/api/v1/nearest-stores?longitude=4.883832", String.class);
                    Assertions.assertThat(response.getBody()).contains("Latitude is required");
        }
        // Longitude is required
        @Test
        void getNearestStores_withMissingLongitude() {
            // Missing longitude
            ResponseEntity<String> response = testRestTemplate.getForEntity(
                    "/api/v1/nearest-stores?latitude=52.37867", String.class);
                    Assertions.assertThat(response.getBody()).contains("Longitude is required");
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