package com.example.thuanthien.data.model;

import java.util.List;

public class Questions {
    private List<Question> questions;
    private List<Video> video;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }
}
