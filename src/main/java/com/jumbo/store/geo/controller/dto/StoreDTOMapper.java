package com.jumbo.store.geo.controller.dto;

import org.mapstruct.Mapper;

import com.jumbo.store.geo.controller.dto.StoresResult.StoreDTO;
import com.jumbo.store.geo.model.Store;

@Mapper(componentModel = "spring")
public interface StoreDTOMapper {
    StoreDTO toDTO(Store store);
}
