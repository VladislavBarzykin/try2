package quizgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Quiz {
    public static void main(String[] args) {
        //load leader-board-file
        //show leader-board frame

        List<Question> questionBank = loadQuestions("questions.json");
        Collections.shuffle(questionBank);

        MainFrame mainFrame = new MainFrame("QuizGame", questionBank);
        if (mainFrame.hasNext()) {
            mainFrame.next();
        }
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel question = new JLabel();
        mainFrame.setQuestionLabel(question);
        Font font = new Font(question.getFont().getName(), question.getFont().getStyle(), 20);
        question.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainFrame.add(question, constraints);
        mainFrame.setVisible(true);
        question.setText(mainFrame.getCurrentQuestion().getQuestion());


        JTextField answer = new JTextField();
        mainFrame.setAnswerTextField(answer);
        answer.setFont(font);
        Dimension size = new Dimension(300, 30);
        answer.setMaximumSize(size);
        answer.setMinimumSize(size);
        answer.setPreferredSize(size);
        constraints.gridy = 1;
        mainFrame.add(answer, constraints);
        answer.setVisible(true);

        JButton reply = new JButton("Ответить");
        reply.setFont(font);
        reply.addActionListener(e -> {
            String currentAnswer = mainFrame.getCurrentQuestion().getAnswer();
            if (currentAnswer.equalsIgnoreCase(answer.getText().trim())) {
                showResultDialog(currentAnswer, mainFrame, "Верный ответ", font);
            } else {
                showResultDialog(currentAnswer, mainFrame, "Неверный ответ", font);
            }
        });
        constraints.gridy = 2;
        mainFrame.add(reply, constraints);
        reply.setVisible(true);

        mainFrame.setVisible(true);

    }

    private static void showResultDialog(String correctAnswer, MainFrame mainFrame, String text, Font font) {
        JDialog jDialog = new JDialog(mainFrame, "Результат", true);
        jDialog.setSize(300, 200);
        jDialog.setResizable(false);
        jDialog.setLocationRelativeTo(mainFrame);
        jDialog.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel label = new JLabel(text);
        label.setFont(font);
        jDialog.add(label, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel correctAnswerLabel = new JLabel(correctAnswer);
        correctAnswerLabel.setFont(font);
        jDialog.add(correctAnswerLabel, constraints);

        JButton closeButton = new JButton("Закрыть");
        closeButton.setFont(font);
        jDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        closeButton.addActionListener(e -> {
            if (mainFrame.hasNext()) {
                mainFrame.next();
            } else {
                mainFrame.init();
                mainFrame.next();
            }
            mainFrame.getQuestionLabel().setText(mainFrame.getCurrentQuestion().getQuestion());
            mainFrame.getAnswerTextField().setText("");
            jDialog.dispose();

        });

        constraints.gridx = 0;
        constraints.gridy = 2;
        jDialog.add(closeButton, constraints);

        jDialog.setVisible(true);

    }

    private static List<Question> loadQuestions(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class);
        try {
            return objectMapper.readValue(Paths.get(filePath).toFile(), listType);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
