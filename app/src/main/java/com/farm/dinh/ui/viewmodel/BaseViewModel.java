package com.farm.dinh.ui.viewmodel;

import androidx.collection.SimpleArrayMap;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farm.dinh.data.Result;
import com.farm.dinh.repository.LoginRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.repository.Repository;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

public class BaseViewModel<D> extends ViewModel {
    private MutableLiveData<ViewResult<D>> result = new MutableLiveData<>();
    private static SimpleArrayMap<Class, Repository> repositoryMap;
    private static SimpleArrayMap<String, MutableLiveData> viewResultMap;

    static {
        repositoryMap = new SimpleArrayMap<>();
        viewResultMap = new SimpleArrayMap<>();
        repositoryMap.put(LoginRepository.class, LoginRepository.getInstance());
        repositoryMap.put(MainRepository.class, MainRepository.getInstance());
    }

    protected final static <R extends Repository> R getRepository(Class<R> clazz) {
        return (R) repositoryMap.get(clazz);
    }

    public MutableLiveData<ViewResult<D>> getResult() {
        return result;
    }

    public <T extends Result> void getResult(String key, T result) {
        if (!viewResultMap.containsKey(key)) {
            viewResultMap.put(key, new MutableLiveData());
        }
        viewResultMap.get(key).setValue(result);
    }
}
