package com.example.thuanthien.ui.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.example.thuanthien.api.APIResponse;
import com.example.thuanthien.data.Result;
import com.example.thuanthien.data.model.Question;
import com.example.thuanthien.data.model.Questions;
import com.example.thuanthien.repository.IRepository;
import com.example.thuanthien.repository.MainRepository;
import com.example.thuanthien.ui.viewmodel.model.ViewResult;

import retrofit2.http.Field;


public class MainViewModel extends BaseViewModel<MainRepository, ViewResult<Questions>> {

    public MainViewModel(MainRepository repository) {
        super(repository);
    }

    private SingleLiveEvent<Question> selectedQuestion = new SingleLiveEvent<>();

    private MutableLiveData<String> answerResult = new MutableLiveData<>();

    public SingleLiveEvent<Question> getSelectedQuestion() {
        return selectedQuestion;
    }

    public MutableLiveData<String> getAnswerResult() {
        return answerResult;
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

    public void addAnswer(int questionId, String answer) {
        getRepository().addAnswer(questionId, answer, new IRepository<APIResponse>(){

            @Override
            public void onSuccess(Result.Success<APIResponse> success) {
                getAnswerResult().postValue(success.getData().getMessage());
            }

            @Override
            public void onError(Result.Error error) {
                getAnswerResult().postValue(error.getError().getMessage());
            }
        });
    }

}
