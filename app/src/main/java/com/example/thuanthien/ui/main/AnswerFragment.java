package com.example.thuanthien.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuanthien.R;
import com.example.thuanthien.data.model.Question;
import com.example.thuanthien.data.model.QuestionType;
import com.example.thuanthien.ui.viewmodel.MainViewModel;
import com.example.thuanthien.ui.viewmodel.ViewModelFactory;

public class AnswerFragment extends Fragment {
    private MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_answer, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity(), new ViewModelFactory()).get(MainViewModel.class);
        Question question = mainViewModel.getSelectedQuestion().getValue();
        TextView txtQuestion = view.findViewById(R.id.txtQuestion);
        txtQuestion.setText(question.getQuestion());
        RecyclerView lvAnswer = view.findViewById(R.id.lv_answer);
        EditText inputAnswer = view.findViewById(R.id.input_answer);
        if (question.getType() == QuestionType.input) {
            lvAnswer.setVisibility(View.GONE);
            inputAnswer.setVisibility(View.VISIBLE);
        } else {
            lvAnswer.setVisibility(View.VISIBLE);
            inputAnswer.setVisibility(View.GONE);
            lvAnswer.setLayoutManager(new LinearLayoutManager(getContext()));
            lvAnswer.setHasFixedSize(true);
            AnswerAdapter adapter = new AnswerAdapter(mainViewModel);
            lvAnswer.setAdapter(adapter);
            adapter.setAnswerList(question.getAnswer());
        }
    }

    private void answerQuestion(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (inflater == null) return;
        menu.clear();
        inflater.inflate(R.menu.save_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveInfo) {
            answerQuestion();
        }
        return super.onOptionsItemSelected(item);
    }
}
