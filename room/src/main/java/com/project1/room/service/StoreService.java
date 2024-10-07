package com.project1.room.service;

import com.project1.room.dto.request.StoreRequest;
import com.project1.room.dto.response.StoreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreService {
    Page<StoreResponse> getStores(Pageable pageable);

    Page<StoreResponse> getStoresContain(String text, Pageable pageable);

    StoreResponse getById(String id);

    StoreResponse addStore(StoreRequest request);

    StoreResponse updateStore(String storeId, StoreRequest request);

    void deleteStoreById(String storeId);
}
