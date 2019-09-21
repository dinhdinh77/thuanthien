package com.farm.dinh.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Question;
import com.farm.dinh.data.model.Questions;
import com.farm.dinh.ui.adapter.MessageAdapter;
import com.farm.dinh.ui.adapter.QuestionAdapter;
import com.farm.dinh.ui.adapter.VideoAdapter;
import com.farm.dinh.ui.viewmodel.MainViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;
import com.farm.dinh.ui.viewmodel.model.ViewResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionFragment extends Fragment {
    private QuestionAdapter adapterQuestion;
    private VideoAdapter videoAdapter;
    private MessageAdapter msgAdapter;
    private LinearLayout llVideo, llQuestion, llMessage;
    private TextView txtNoData;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llVideo = view.findViewById(R.id.ll_video);
        llQuestion = view.findViewById(R.id.ll_question);
        llMessage = view.findViewById(R.id.ll_message);
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

        RecyclerView lvMessage = view.findViewById(R.id.lv_message);
        lvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        lvMessage.setHasFixedSize(true);
        msgAdapter = new MessageAdapter();
        lvMessage.setAdapter(msgAdapter);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        mainViewModel.getSelectedQuestion().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                navController.navigate(R.id.action_questionFragment_to_answerFragment);
            }
        });

        mainViewModel.getQuestionsList();
    }

    private void showDataFailed(String errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithData(Questions questions) {
        if (questions != null) {
            if (questions.getQuestions() != null && questions.getQuestions().size() > 0) {
                txtNoData.setVisibility(View.GONE);
                llQuestion.setVisibility(View.VISIBLE);
                adapterQuestion.setQuestionList(questions.getQuestions());
            }

            if (questions.getVideos() != null && questions.getVideos().size() > 0) {
                txtNoData.setVisibility(View.GONE);
                llVideo.setVisibility(View.VISIBLE);
                videoAdapter.setVideoList(questions.getVideos());
            }

            if (questions.getMessages() != null && questions.getMessages().size() > 0) {
                txtNoData.setVisibility(View.GONE);
                llMessage.setVisibility(View.VISIBLE);
                msgAdapter.setMessageList(questions.getMessages());
            }
        }
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
        inflater.inflate(R.menu.main_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.showUserDetail) {
            navController.navigate(R.id.action_questionFragment_to_userDetailFragment);
        }
        return super.onOptionsItemSelected(item);
    }
}
