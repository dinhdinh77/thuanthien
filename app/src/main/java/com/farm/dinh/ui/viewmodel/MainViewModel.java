package com.farm.dinh.ui.viewmodel;


import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.farm.dinh.api.APIResponse;
import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Question;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.model.ViewResult;


public class MainViewModel extends BaseViewModel<MainRepository, ViewResult<Questions>> {

    public MainViewModel(MainRepository repository) {
        super(repository);
    }

    private SingleLiveEvent<Question> selectedQuestion = new SingleLiveEvent<>();

    private MutableLiveData<ViewResult<APIResponse>> answerResult = new MutableLiveData<>();

    public SingleLiveEvent<Question> getSelectedQuestion() {
        return selectedQuestion;
    }

    public MutableLiveData<ViewResult<APIResponse>> getAnswerResult() {
        return answerResult;
    }

    public void resetAnswerResult() {
        this.answerResult = new MutableLiveData<>();
    }

    public void getQuestionsList() {
        getRepository().getQuestionsList(new IRepository<Questions>() {
            @Override
            public void onSuccess(Result.Success<Questions> success) {
                getResult().setValue(new ViewResult<>(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }

    public void addAnswer(String answer, String error) {
        if (getSelectedQuestion().getValue() == null) return;
        if (TextUtils.isEmpty(answer)) {
            getAnswerResult().setValue(new ViewResult(error));
            return;
        }
        getRepository().addAnswer(getSelectedQuestion().getValue().getQuestionID(), answer, new IRepository<APIResponse>() {

            @Override
            public void onSuccess(Result.Success<APIResponse> success) {
                getAnswerResult().setValue(new ViewResult<>(success.getData()));
            }

            @Override
            public void onError(Result.Error error) {
                getAnswerResult().setValue(new ViewResult(error.getError().getMessage()));
            }
        });
    }

}
