package com.jumbo.store.geo.service.impl;

import org.springframework.stereotype.Service;
import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;
import com.jumbo.store.geo.service.StoreService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

/**
 * Implementation of StoreService for managing store-related operations.
 */
@Service
@Slf4j
public class StoreServiceImpl implements StoreService {

    @Value("${store.max-distance-km}")
    private double maxDistance;
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getNearestStores(String latitude, String longitude) {
        log.info("Fetching nearest stores for latitude: {}, longitude: {}", latitude, longitude);

        // Validate and parse latitude and longitude
        double lat = parseCoordinate(latitude, "latitude");
        double lon = parseCoordinate(longitude, "longitude");

        GeoJsonPoint location = new GeoJsonPoint(lon, lat);
        log.debug("GeoJsonPoint created: {}", location);

        Distance distance = new Distance(maxDistance, Metrics.KILOMETERS);
        log.debug("Searching for stores within distance: {}", distance);

        Pageable limit = PageRequest.of(0, 5);
        return storeRepository.findByLocationNear(location, distance, limit);
    }

    private double parseCoordinate(String coordinate, String type) {
        try {
            double value = Double.parseDouble(coordinate);
            if ("latitude".equals(type) && (value < -90 || value > 90)) {
                throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees.");
            }
            if ("longitude".equals(type) && (value < -180 || value > 180)) {
                throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees.");
            }
            return value;
        } catch (NumberFormatException e) {
            log.error("Invalid {} value: {}", type, coordinate);
            throw new IllegalArgumentException("Invalid " + type + " value: " + coordinate, e);
        }
    }
}