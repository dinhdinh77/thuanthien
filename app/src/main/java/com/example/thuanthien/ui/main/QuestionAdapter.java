package com.example.thuanthien.ui.main;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuanthien.R;
import com.example.thuanthien.data.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questionList = new ArrayList<>();

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
        holder.initData(questionList.get(position));
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
