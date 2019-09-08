package com.example.thuanthien.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thuanthien.repository.Repository;
import com.example.thuanthien.ui.viewmodel.model.ViewResult;

public class BaseViewModel<R extends Repository, D extends ViewResult> extends ViewModel {
    private R repository;
    private MutableLiveData<D> result = new MutableLiveData<>();

    public BaseViewModel(R repository) {
        this.repository = repository;
    }

    public R getRepository() {
        return repository;
    }

    public MutableLiveData<D> getResult() {
        return result;
    }
}
