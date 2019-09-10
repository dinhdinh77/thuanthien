package com.example.thuanthien.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.thuanthien.data.model.Video;
import com.example.thuanthien.ui.viewmodel.MainViewModel;
import com.example.thuanthien.ui.viewmodel.ViewModelFactory;
import com.example.thuanthien.ui.viewmodel.model.ViewResult;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {
    private QuestionAdapter adapterQuestion;
    private VideoAdapter videoAdapter;
    private LinearLayout llVideo, llQuestion;
    private TextView txtNoData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llVideo = view.findViewById(R.id.ll_video);
        llQuestion = view.findViewById(R.id.ll_question);
        txtNoData = view.findViewById(R.id.txt_NoData);
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity(), new ViewModelFactory()).get(MainViewModel.class);
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
        adapterQuestion = new QuestionAdapter(mainViewModel);
        lvQuestion.setAdapter(adapterQuestion);

        RecyclerView lvVideo = view.findViewById(R.id.lv_video);
        lvVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        lvVideo.setHasFixedSize(true);
        videoAdapter = new VideoAdapter();
        lvVideo.setAdapter(videoAdapter);
        mainViewModel.getQuestionsList();
    }

    private void showDataFailed(String errorString){
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithData(Questions questions){
        if(questions != null){
            if(questions.getQuestions() != null && questions.getQuestions().size() > 0) {
                txtNoData.setVisibility(View.GONE);
                llQuestion.setVisibility(View.VISIBLE);
                adapterQuestion.setQuestionList(questions.getQuestions());
            }

            List<Video> list = new ArrayList<>();
            list.add(new Video("https://www.youtube.com/watch?v=bSMZknDI6bg"));
            list.add(new Video("https://www.youtube.com/watch?v=12FDmPuAr4I"));
            list.add(new Video("https://www.youtube.com/watch?v=9TsTg7wZmGA"));
            list.add(new Video("https://www.youtube.com/watch?v=K0eu0pPZaw0"));
            questions.setVideo(list);

            if(questions.getVideo() != null && questions.getVideo().size() > 0) {
                txtNoData.setVisibility(View.GONE);
                llVideo.setVisibility(View.VISIBLE);
                videoAdapter.setVideoList(questions.getVideo());
            }
        }
    }
}
