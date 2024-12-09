package com.jumbo.store.geo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mongodb.lang.NonNull;

import lombok.extern.slf4j.Slf4j;

@Testcontainers
public class TestContainersMongoConfig {
    @Container
    private static final MongoDBContainer MONGO_DB = new MongoDBContainer("mongo:latest");

    @Slf4j
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull final ConfigurableApplicationContext configurableApplicationContext) {
            MONGO_DB.start();

            var mongoURI = MONGO_DB.getConnectionString() + "/test";
            TestPropertyValues.of("spring.data.mongodb.uri=" + mongoURI).applyTo(configurableApplicationContext);
        }
    }
}