package quizgame;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MainFrame extends JFrame {
    public static final int NUMBER_OF_QUESTIONS = 3;
    private final List<Question> questionBank;
    private int numberOfQuestions;
    private JLabel questionLabel;
    private JTextField answerTextField;
    private Iterator<Question> iterator;
    private Question currentQuestion;

    public MainFrame(String title, List<Question> questionBank) throws HeadlessException {
        super(title);
        iterator = questionBank.iterator();
        this.questionBank = questionBank;
        numberOfQuestions = 0;
    }

    public JLabel getQuestionLabel() {
        return questionLabel;
    }

    public void setQuestionLabel(JLabel questionLabel) {
        this.questionLabel = questionLabel;
    }

    public JTextField getAnswerTextField() {
        return answerTextField;
    }

    public void setAnswerTextField(JTextField answerTextField) {
        this.answerTextField = answerTextField;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public boolean hasNext() {
        return numberOfQuestions < NUMBER_OF_QUESTIONS || iterator.hasNext();
    }

    public Question next() {
        numberOfQuestions++;
        currentQuestion = iterator.next();
        return currentQuestion;
    }

    public void init() {
        numberOfQuestions = 0;
        Collections.shuffle(questionBank);
        iterator = questionBank.iterator();
    }

}
