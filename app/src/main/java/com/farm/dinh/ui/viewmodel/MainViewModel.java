package com.farm.dinh.ui.viewmodel;


import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.farm.dinh.data.Result;
import com.farm.dinh.data.model.Question;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.repository.IRepository;
import com.farm.dinh.repository.MainRepository;
import com.farm.dinh.ui.viewmodel.custom.SingleLiveEvent;
import com.farm.dinh.ui.viewmodel.model.ViewResult;


public class MainViewModel extends BaseViewModel<MainRepository, Questions> {

    public MainViewModel(MainRepository repository) {
        super(repository);
    }

    private SingleLiveEvent<Question> selectedQuestion = new SingleLiveEvent<>();

    private MutableLiveData<ViewResult<String>> answerResult = new MutableLiveData<>();

    public SingleLiveEvent<Question> getSelectedQuestion() {
        return selectedQuestion;
    }

    public MutableLiveData<ViewResult<String>> getAnswerResult() {
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
            getAnswerResult().setValue(new ViewResult(error, false));
            return;
        }
        getRepository().addAnswer(getSelectedQuestion().getValue().getQuestionID(), answer, new IRepository<String>() {

            @Override
            public void onSuccess(Result.Success<String> success) {

                getAnswerResult().setValue(new ViewResult(success.getData(), true));
            }

            @Override
            public void onError(Result.Error error) {
                getAnswerResult().setValue(new ViewResult(error.getError().getMessage(), false));
            }
        });
    }

}
