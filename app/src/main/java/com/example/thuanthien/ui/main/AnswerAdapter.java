package com.example.thuanthien.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuanthien.R;
import com.example.thuanthien.data.model.Answer;
import com.example.thuanthien.ui.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {
    private List<Answer> answerList = new ArrayList<>();
    private MainViewModel mainViewModel;

    public AnswerAdapter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        holder.initData(answerList.get(position));
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    protected static class AnswerViewHolder extends RecyclerView.ViewHolder {
        private TextView txtAnswer;
        private CheckBox selectedAnswer;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAnswer = itemView.findViewById(R.id.txtAnswer);
            selectedAnswer = itemView.findViewById(R.id.selectedAnswer);
        }

        public void initData(Answer answer) {
            txtAnswer.setText(answer.getContent());
        }
    }
}
