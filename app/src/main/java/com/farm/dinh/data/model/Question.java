package com.farm.dinh.data.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private int questionId;
    private String question;
    private QuestionType type;
    private List<Answer> answer;

    public int getQuestionID() {
        return questionId;
    }

    public void setQuestionID(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }
}
