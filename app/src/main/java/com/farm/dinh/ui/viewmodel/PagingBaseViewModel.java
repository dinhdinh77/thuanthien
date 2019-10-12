package com.farm.dinh.ui.viewmodel;

import android.text.TextUtils;

import com.farm.dinh.data.Result;
import com.farm.dinh.repository.IPagingRepository;
import com.farm.dinh.repository.Repository;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import java.util.List;

public abstract class PagingBaseViewModel<D, R extends Repository> extends BaseViewModel<R, List<D>> {
    private int currPage;
    private int totalPage;
    private String currKeyword;

    public PagingBaseViewModel(R repository) {
        super(repository);
        resetData();
    }

    public final void resetData() {
        this.currPage = 0;
        this.totalPage = 1;
        this.currKeyword = "";
    }

    public final void getListPaging(String keyword) {
        if (!currKeyword.equals(keyword)) resetData();
        currPage++;
        if (currPage <= totalPage) {
            currKeyword = keyword;
            IPagingRepository<List<D>> listener = new IPagingRepository<List<D>>() {

                @Override
                public void onSuccess(Result.Success<List<D>> success, int pageTotal) {
                    totalPage = pageTotal;
                    getResult().setValue(new ViewResult<>(success.getData(), currPage > 1));
                }

                @Override
                public void onError(Result.Error error) {
                    currPage--;
                    getResult().setValue(new ViewResult(error.getError().getMessage(), currPage > 1));
                }
            };
            if (TextUtils.isEmpty(keyword)) {
                getListEmptyKeyword(currPage, listener);
            } else {
                getListWithKeyword(keyword, currPage, listener);
            }
        } else {
            getResult().setValue(null);
        }
    }

    protected abstract void getListEmptyKeyword(int currPage, IPagingRepository<List<D>> listener);

    protected abstract void getListWithKeyword(String keyword, int currPage, IPagingRepository<List<D>> listener);
}
