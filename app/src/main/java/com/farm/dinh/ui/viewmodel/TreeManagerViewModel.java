package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public class TreeManagerViewModel extends BaseViewModel<MainRepository, List<TreeInfo>> {
    public TreeManagerViewModel(MainRepository repository) {
        super(repository);
    }

    public void getTreesByFarmer(int farmerId) {
        getRepository().getTreesByFarmer(farmerId, new IRepository<List<TreeInfo>>() {
            @Override
            public void onSuccess(Result.Success<List<TreeInfo>> success) {
                getResult().setValue(new ViewResult(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }
}
