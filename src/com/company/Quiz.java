package com.company;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private final String type;
    private List<Question> questions = new ArrayList<>();

    public Quiz(String type) {
        this.type = type;
        // question list will be retrieved from a file
    }
}
