package com.example.thuanthien.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuanthien.R;
import com.example.thuanthien.data.model.Questions;
import com.example.thuanthien.ui.viewmodel.MainViewModel;
import com.example.thuanthien.ui.viewmodel.ViewModelFactory;
import com.example.thuanthien.ui.viewmodel.model.ViewResult;

public class QuestionFragment extends Fragment {
    private QuestionAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity(), new ViewModelFactory()).get(MainViewModel.class);
        mainViewModel.getQuestionsList();
        mainViewModel.getResult().observe(this, new Observer<ViewResult<Questions>>() {
            @Override
            public void onChanged(ViewResult<Questions> questionsViewResult) {
                if (questionsViewResult == null) {
                    return;
                }
                if (questionsViewResult.getError() != null) {
                    showDataFailed(questionsViewResult.getError());
                }
                if (questionsViewResult.getSuccess() != null) {
                    updateUiWithData(questionsViewResult.getSuccess());
                }

            }
        });
        RecyclerView lvQuestion = view.findViewById(R.id.lv_question);
        lvQuestion.setLayoutManager(new LinearLayoutManager(getContext()));
        lvQuestion.setHasFixedSize(true);
        lvQuestion.addItemDecoration(new DividerItemDecoration(lvQuestion.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new QuestionAdapter(mainViewModel);
        lvQuestion.setAdapter(adapter);
    }

    private void showDataFailed(String errorString){
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithData(Questions questions){
        adapter.setQuestionList(questions.getQuestions());
    }
}
