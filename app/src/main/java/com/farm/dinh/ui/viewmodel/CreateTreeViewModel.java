package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.farm.dinh.R;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Tree;
import com.farm.dinh.data.model.TreeInfo;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.CreateTreeState;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.ArrayList;
import java.util.List;

public class CreateTreeViewModel extends BaseViewModel<MainRepository, List<Tree>> {
    public CreateTreeViewModel(MainRepository repository) {
        super(repository);
    }

    private int farmerId;
    private MutableLiveData<TreeInfo> treeInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<CreateTreeState> createTreeState = new MutableLiveData<>();
    private MutableLiveData<ViewResult> processTreeState = new MutableLiveData<>();

    public MutableLiveData<TreeInfo> getTreeInfoLiveData() {
        return treeInfoLiveData;
    }


    public MutableLiveData<CreateTreeState> getCreateTreeState() {
        return createTreeState;
    }

    public MutableLiveData<ViewResult> getProcessTreeState() {
        return processTreeState;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public void dataChange(Tree tree, String age, String quantity) {
        CreateTreeState createTreeState = new CreateTreeState();
        if (tree == null) {
            createTreeState.setTreeError(R.string.invalid_tree);
        }
        if (TextUtils.isEmpty(age)) {
            createTreeState.setAgeError(R.string.invalid_tree_age);
        }
        if (TextUtils.isEmpty(quantity)) {
            createTreeState.setQuantityError(R.string.invalid_tree_quantity);
        }
        getCreateTreeState().setValue(createTreeState);
    }

    public void getTreesList() {
        getRepository().getTreesList(new IRepository<List<Tree>>() {
            @Override
            public void onSuccess(Result.Success<List<Tree>> success) {
                getResult().setValue(new ViewResult<>(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }

    public void processTree(Tree tree, String age, String quantity) {
        CreateTreeState createTreeState = getCreateTreeState().getValue();
        if (createTreeState == null || !createTreeState.isDataVaild()) return;
        IRepository<String> listener = new IRepository<String>() {
            @Override
            public void onSuccess(Result.Success<String> success) {
                getProcessTreeState().setValue(new ViewResult(success.getData(), true));
            }

            @Override
            public void onError(Result.Error error) {
                getProcessTreeState().setValue(new ViewResult(error.getError().getMessage(), false));
            }
        };
        if (getTreeInfoLiveData().getValue() == null) {
            getRepository().addTree(this.farmerId, tree.getId(), age, Integer.valueOf(quantity), listener);
        } else {
            getRepository().editTree(getTreeInfoLiveData().getValue().getId(), tree.getId(), age, Integer.valueOf(quantity), listener);
        }
    }
}
