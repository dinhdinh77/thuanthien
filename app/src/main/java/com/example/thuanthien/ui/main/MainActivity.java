package com.example.thuanthien.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView lvQuestion = findViewById(R.id.lv_question);
        lvQuestion.setLayoutManager(new LinearLayoutManager(this));
        lvQuestion.setHasFixedSize(true);
        lvQuestion.addItemDecoration(new DividerItemDecoration(lvQuestion.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new QuestionAdapter();
        lvQuestion.setAdapter(adapter);

        mainViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(MainViewModel.class);
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
    }

    private void showDataFailed(String errorString){
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithData(Questions questions){
        adapter.setQuestionList(questions.getQuestions());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accountInfo:
                Toast.makeText(getApplicationContext(), "OKIE", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
