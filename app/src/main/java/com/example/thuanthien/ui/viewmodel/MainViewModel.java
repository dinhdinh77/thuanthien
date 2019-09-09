package com.example.thuanthien.ui.viewmodel;


import com.example.thuanthien.data.Result;
import com.example.thuanthien.data.model.Question;
import com.example.thuanthien.data.model.Questions;
import com.example.thuanthien.repository.IRepository;
import com.example.thuanthien.repository.MainRepository;
import com.example.thuanthien.ui.viewmodel.model.ViewResult;


public class MainViewModel extends BaseViewModel<MainRepository, ViewResult<Questions>> {

    public MainViewModel(MainRepository repository) {
        super(repository);
    }

    private SingleLiveEvent<Question> selectedQuestion = new SingleLiveEvent<>();

    public SingleLiveEvent<Question> getSelectedQuestion() {
        return selectedQuestion;
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
}
