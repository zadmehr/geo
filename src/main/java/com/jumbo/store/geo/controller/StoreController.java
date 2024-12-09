package com.jumbo.store.geo.controller;

import com.jumbo.store.geo.controller.dto.NearestStoreRequest;
import com.jumbo.store.geo.controller.dto.StoreDTOMapper;
import com.jumbo.store.geo.controller.dto.StoresResult;
import com.jumbo.store.geo.service.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Validated
@Tag(name = "Store API", description = "API for managing stores and their locations")
public class StoreController {

    private final StoreService storeService;
    private final StoreDTOMapper storeDTOMapper;

    public StoreController(StoreService storeService, StoreDTOMapper storeDTOMapper) {
        this.storeService = storeService;
        this.storeDTOMapper = storeDTOMapper;
    }

    @GetMapping("/nearest-stores")
    @Operation(summary = "Get nearest stores", description = "Returns the 5 closest stores to the given latitude and longitude")

    public StoresResult getNearestStores(@Valid NearestStoreRequest request) {

        return StoresResult.builder().stores(
                storeService.getNearestStores(request.latitude(), request.longitude()).stream()
                        .map(storeDTOMapper::toDTO).collect(Collectors.toList()))
                .build();

    }
}