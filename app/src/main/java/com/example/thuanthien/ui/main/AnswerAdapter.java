package com.example.thuanthien.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    public void onBindViewHolder(@NonNull final AnswerViewHolder holder, final int position) {
        final CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedAnswer(answerList.get(position), isChecked);
                notifyDataSetChanged();
            }
        };

        holder.initData(answerList.get(position), listener);
    }

    private void selectedAnswer(Answer answer, boolean isChecked) {
        if (isChecked) {
            for (Answer ans : answerList) {
                if (ans.equals(answer)) ans.setSelected(true);
                else ans.setSelected(false);
            }
        } else {
            answer.setSelected(false);
        }

    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    protected static class AnswerViewHolder extends RecyclerView.ViewHolder {
        private CheckBox selectedAnswer;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            selectedAnswer = itemView.findViewById(R.id.selectedAnswer);
        }

        public void initData(Answer answer, CompoundButton.OnCheckedChangeListener listener) {
            selectedAnswer.setText(answer.getContent());
            selectedAnswer.setOnCheckedChangeListener(null);
            selectedAnswer.setChecked(answer.isSelected());
            selectedAnswer.setOnCheckedChangeListener(listener);
        }
    }
}
