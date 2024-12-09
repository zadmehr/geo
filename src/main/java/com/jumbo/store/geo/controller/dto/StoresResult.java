package com.jumbo.store.geo.controller.dto;

import java.util.List;

import com.jumbo.store.geo.model.Store;

import lombok.Builder;
@Builder
public record StoresResult(List<StoreDTO> stores) {

   public record StoreDTO(String city, double latitude, double longitude) {

   }

}