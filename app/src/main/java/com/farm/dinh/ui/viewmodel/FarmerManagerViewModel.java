package com.farm.dinh.ui.viewmodel;


import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.MainRepository;

import java.util.List;


public class FarmerManagerViewModel extends PagingBaseViewModel<FarmerInfo> {

    @Override
    protected void getListEmptyKeyword(int currPage, IPagingRepository<List<FarmerInfo>> listener) {
        getRepository(MainRepository.class).getFarmersList(currPage, listener);
    }

    @Override
    protected void getListWithKeyword(String keyword, int currPage, IPagingRepository<List<FarmerInfo>> listener) {
        getRepository(MainRepository.class).searchFarmers(keyword, currPage, listener);
    }
}
