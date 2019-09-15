package com.farm.dinh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Question;
import com.farm.dinh.ui.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questionList = new ArrayList<>();
    private MainViewModel mainViewModel;

    public QuestionAdapter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        final Question question = questionList.get(position);
        holder.initData(question);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.getSelectedQuestion().setValue(question);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    protected static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView titleQuestion;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleQuestion = itemView.findViewById(R.id.titleQuestion);
        }

        public void initData(Question question) {
            titleQuestion.setText(question.getQuestion());
        }
    }
}
