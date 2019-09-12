package com.farm.dinh.data.model;

import java.util.List;

public class Questions {
    private List<Question> questions;
    private List<Video> videos;
    private List<Message> messages;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideo(List<Video> videos) {
        this.videos = videos;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
