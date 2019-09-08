package com.example.thuanthien.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.thuanthien.R;
import com.example.thuanthien.data.model.Questions;
import com.example.thuanthien.ui.viewmodel.MainViewModel;
import com.example.thuanthien.ui.viewmodel.ViewModelFactory;
import com.example.thuanthien.ui.viewmodel.model.ViewResult;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(MainViewModel.class);
        mainViewModel.getQuestionsList();
        mainViewModel.getResult().observe(this, new Observer<ViewResult<Questions>>() {
            @Override
            public void onChanged(ViewResult<Questions> questionsViewResult) {
                Log.d("getQuestionsList","getQuestionsList");
            }
        });
    }
}
