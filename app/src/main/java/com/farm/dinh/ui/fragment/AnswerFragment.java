package com.farm.dinh.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Question;
import com.farm.dinh.data.model.QuestionType;
import com.farm.dinh.ui.adapter.AnswerAdapter;
import com.farm.dinh.ui.viewmodel.MainViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

public class AnswerFragment extends Fragment {
    private MainViewModel mainViewModel;
    private EditText inputAnswer;
    private AnswerAdapter adapter;
    private QuestionType type;

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
        inputAnswer = view.findViewById(R.id.input_answer);
        type = question.getType();
        if (question.getType() == QuestionType.input) {
            lvAnswer.setVisibility(View.GONE);
            inputAnswer.setVisibility(View.VISIBLE);
        } else {
            lvAnswer.setVisibility(View.VISIBLE);
            inputAnswer.setVisibility(View.GONE);
            lvAnswer.setLayoutManager(new LinearLayoutManager(getContext()));
            lvAnswer.setHasFixedSize(true);
            adapter = new AnswerAdapter();
            lvAnswer.setAdapter(adapter);
            adapter.setAnswerList(question.getAnswer());
        }
        mainViewModel.resetAnswerResult();
        mainViewModel.getAnswerResult().observe(this, new Observer<ViewResult<String>>() {
            @Override
            public void onChanged(ViewResult<String> viewResult) {
                if (viewResult == null) {
                    return;
                }
                if (!viewResult.isSuccess()) {
                    Toast.makeText(getContext(), viewResult.getError(), Toast.LENGTH_SHORT).show();
                } else {
                    getActivity().onBackPressed();
                }
            }
        });
    }

    private void answerQuestion() {
        String answer = "";
        if (type == QuestionType.input) {
            answer = inputAnswer.getText().toString();
        } else {
            answer = adapter.getSelectedAnswer();
        }
        mainViewModel.addAnswer(answer, getString(R.string.prompt_answer));
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
