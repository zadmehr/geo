package com.jumbo.store.geo.service;

import com.jumbo.store.geo.model.Store;
import com.jumbo.store.geo.repository.StoreRepository;
import com.jumbo.store.geo.service.impl.StoreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    void testGetNearestStores_validCoordinates() {
        // Arrange
        String latitude = "52.3784";
        String longitude = "4.9009";
        List<Store> mockStores = Arrays.asList(
                Store.builder().city("Amsterdam").postalCode("1012AB").street("Damstraat").addressName("Store A")
                        .longitude(4.8952).latitude(52.3702).todayOpen("08:00").todayClose("22:00")
                        .collectionPoint(true)
                        .complexNumber("123").build(),
                Store.builder().city("Rotterdam").postalCode("3012AB").street("Coolsingel").addressName("Store B")
                        .longitude(4.47917).latitude(51.9225).todayOpen("08:00").todayClose("22:00")
                        .collectionPoint(true)
                        .complexNumber("456").build());

        // Mock the repository call
        when(storeRepository.findByLocationNear(any(Point.class), any(Distance.class), any(Pageable.class)))
                .thenReturn(mockStores);

        // Act
        List<Store> result = storeService.getNearestStores(latitude, longitude);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); // Expecting 2 stores as mocked
        verify(storeRepository, times(1)).findByLocationNear(any(Point.class), any(Distance.class),
                any(PageRequest.class));
    }

    @Test
    void testGetNearestStores_invalidLatitude() {
        // Arrange
        String latitude = "100.0"; // Invalid latitude
        String longitude = "4.9009";

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            storeService.getNearestStores(latitude, longitude);
        });

        assertEquals("Latitude must be between -90 and 90 degrees.", thrown.getMessage());
    }

    @Test
    void testGetNearestStores_invalidLongitude() {
        // Arrange
        String latitude = "52.3784";
        String longitude = "200.0"; // Invalid longitude

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            storeService.getNearestStores(latitude, longitude);
        });

        assertEquals("Longitude must be between -180 and 180 degrees.", thrown.getMessage());
    }

    @Test
    void testGetNearestStores_emptyResult() {
        // Arrange
        String latitude = "52.3784";
        String longitude = "4.9009";
        List<Store> mockStores = Arrays.asList(); // Empty list for no stores

        when(storeRepository.findByLocationNear(any(Point.class), any(Distance.class), any(Pageable.class)))
                .thenReturn(mockStores);

        // Act
        List<Store> result = storeService.getNearestStores(latitude, longitude);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty()); // Expecting empty list as mocked
    }
}
