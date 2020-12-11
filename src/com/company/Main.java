package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the quizapp!");
        System.out.println("To select text-base interface enter 0");
        System.out.println("To select graphical interface enter 1");

        System.out.println("Enter your choice");
        int choice = scanner.nextInt();
        while(choice != 0 && choice != 1) {
            System.out.println("Wrong number! Please try again.");
            choice = scanner.nextInt();
        }

        View view;
        if(choice == 0) {
            view = new TextView();
        } else {
            view = new GuiView();
        }
    }
}
