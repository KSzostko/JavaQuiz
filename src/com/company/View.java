package com.company;

public abstract class View {
    public abstract void displayStartView();
    public abstract void displayQuizTypeView();
    public abstract void displayLeadersView();
    public abstract void displayQuestionView(Question question);
    public abstract void displayEndView(Score score);
}
