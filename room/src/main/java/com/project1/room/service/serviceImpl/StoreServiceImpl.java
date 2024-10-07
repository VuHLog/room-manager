package com.project1.room.service.serviceImpl;

import com.project1.room.dao.BranchesRepository;
import com.project1.room.dao.EquipmentsRepository;
import com.project1.room.dao.StoreRepository;
import com.project1.room.dto.request.StoreRequest;
import com.project1.room.dto.response.StoreResponse;
import com.project1.room.entity.Branches;
import com.project1.room.entity.Equipments;
import com.project1.room.entity.Store;
import com.project1.room.mapper.StoreMapper;
import com.project1.room.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private EquipmentsRepository equipmentsRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Page<StoreResponse> getStores(Pageable pageable) {
        return storeRepository.findAll(pageable).map(storeMapper::toStoreResponse);
    }

    @Override
    public Page<StoreResponse> getStoresContain(String text, Pageable pageable) {
        return storeRepository.findByBranch_NameContainsIgnoreCase(text, pageable).map(storeMapper::toStoreResponse);
    }

    @Override
    public StoreResponse getById(String id) {
        Store store = storeRepository.findById(id).orElse(null);
        return storeMapper.toStoreResponse(store);
    }

    @Override
    public StoreResponse addStore(StoreRequest request) {
        Store store = storeMapper.toStore(request);

        //add branch to store
        Branches branch = branchesRepository.findById(request.getBranchId()).orElse(null);
        store.setBranch(branch);

        //add equipment to store
        Equipments equipment = equipmentsRepository.findById(request.getEquipmentId()).orElse(null);
        store.setEquipment(equipment);

        return storeMapper.toStoreResponse(storeRepository.save(store));
    }

    @Override
    public StoreResponse updateStore(String storeId, StoreRequest request) {
        Store store = storeRepository.findById(storeId).orElseThrow(null);
        storeMapper.updateStore(store, request);

        //check branch
        if(!store.getBranch().getId().equals(request.getBranchId())) {
            Branches branch = branchesRepository.findById(request.getBranchId()).orElse(null);
            store.setBranch(branch);
        }

        if(!store.getEquipment().getId().equals(request.getEquipmentId())) {
            Equipments equipment = equipmentsRepository.findById(request.getEquipmentId()).orElse(null);
            store.setEquipment(equipment);
        }
        return storeMapper.toStoreResponse(storeRepository.save(store));
    }

    @Override
    public void deleteStoreById(String storeId) {
        storeRepository.deleteById(storeId);
    }
}
