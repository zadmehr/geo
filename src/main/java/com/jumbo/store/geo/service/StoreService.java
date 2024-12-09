package com.jumbo.store.geo.service;

import com.jumbo.store.geo.model.Store;
import java.util.List;

public interface StoreService  {
    List<Store> getNearestStores(String latitude, String longitude);
}