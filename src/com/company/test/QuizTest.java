package com.company.test;

import com.company.Question;
import com.company.Quiz;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {

    @org.junit.jupiter.api.Test
    public void loadQuestions() {
        Quiz quiz = new Quiz("test");

        boolean success = quiz.loadQuestions();
        assertTrue(success);

        List<Question> questions = quiz.getQuestions();
        List<Question> questionsCorrect = new ArrayList<>();
        questionsCorrect.add(new Question(
                "Dokad noca tupta jez?",
                new String[] { "Do sklepu", "Do baru", "Przejsc sie", "Na strajk" },
                3
        ));
        questionsCorrect.add(new Question(
                "Jaki jest najlepszy jezyk programowania?",
                new String[] { "Python", "Java", "Assembler", "C" },
                2
        ));

        assertEquals(questions.size(), questionsCorrect.size());
        for(int i = 0; i < questions.size(); i++) {
            assertEquals(questions.get(i).toString(), questionsCorrect.get(i).toString());
        }
    }
}