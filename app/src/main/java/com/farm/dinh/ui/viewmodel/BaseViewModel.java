package com.farm.dinh.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farm.dinh.repository.Repository;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

public class BaseViewModel<R extends Repository, D> extends ViewModel {
    private R repository;
    private MutableLiveData<ViewResult<D>> result = new MutableLiveData<>();

    public BaseViewModel(R repository) {
        this.repository = repository;
    }

    protected R getRepository() {
        return repository;
    }

    public MutableLiveData<ViewResult<D>> getResult() {
        return result;
    }
}
