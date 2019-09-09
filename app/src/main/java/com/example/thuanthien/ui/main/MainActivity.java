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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuanthien.R;
import com.example.thuanthien.data.model.Question;
import com.example.thuanthien.data.model.Questions;
import com.example.thuanthien.ui.viewmodel.MainViewModel;
import com.example.thuanthien.ui.viewmodel.ViewModelFactory;
import com.example.thuanthien.ui.viewmodel.model.ViewResult;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        MainViewModel mainViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(MainViewModel.class);
        mainViewModel.getSelectedQuestion().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                navController.navigate(R.id.action_questionFragment_to_answerFragment);
            }
        });
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
                navController.navigate(R.id.action_questionFragment_to_userDetailFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
