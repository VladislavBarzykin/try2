package quizgame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {
    @JsonProperty("question")
    private String question;
    @JsonProperty("answer")
    private String answer;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}