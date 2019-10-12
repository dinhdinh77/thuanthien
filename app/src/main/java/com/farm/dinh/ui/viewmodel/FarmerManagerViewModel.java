package com.farm.dinh.ui.viewmodel;


import com.farm.dinh.data.model.FarmerInfo;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.MainRepository;

import java.util.List;


public class FarmerManagerViewModel extends PagingBaseViewModel<FarmerInfo, MainRepository> {

    public FarmerManagerViewModel(MainRepository repository) {
        super(repository);
    }

    @Override
    protected void getListEmptyKeyword(int currPage, IPagingRepository<List<FarmerInfo>> listener) {
        getRepository().getFarmersList(currPage, listener);
    }

    @Override
    protected void getListWithKeyword(String keyword, int currPage, IPagingRepository<List<FarmerInfo>> listener) {
        getRepository().searchFarmers(keyword, currPage, listener);
    }
}
