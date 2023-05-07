import java.io.IOException;
import java.util.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class QuizGame {
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

    public static void main(String[] args) {
        List<Question> questionBank = loadQuestions("questions.json");

        Scanner scanner = new Scanner(System.in);
        InputCollector inputCollector = new InputCollector(scanner);

        boolean keepPlaying = true;
        int highestScore = 0;

        while (keepPlaying) {
            Collections.shuffle(questionBank);
            int score = 0;

            for (int i = 0; i < questionBank.size(); i++) {
                Question currentQuestion = questionBank.get(i);
                System.out.println(currentQuestion.getQuestion());

                String userAnswer = inputCollector.getInputWithTimeout(1, TimeUnit.MINUTES);

                if (userAnswer == null ) {
                    System.out.println("Time's up");
                } else {
                    if (userAnswer.equalsIgnoreCase(currentQuestion.getAnswer())) {
                        score++;
                    } else {
                        System.out.println("Wrong answer");
                    }
                }
            }

            System.out.println("Your score: " + score + " out of " + questionBank.size());
            highestScore = Math.max(highestScore, score);
            System.out.println("Highest score: " + highestScore);

            System.out.println("Do you want to play again? (yes/no)");
            String playAgain = scanner.nextLine();
            keepPlaying = playAgain.equalsIgnoreCase("yes");
        }
    }
}