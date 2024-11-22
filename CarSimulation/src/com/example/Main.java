package com.example;

import com.example.controller.GridController;
import com.example.view.ConsoleView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // creates an object of Scanner
        Scanner scanner = new Scanner(System.in);
        ConsoleView consoleView = new ConsoleView(scanner);
        GridController gridController = new GridController(consoleView);
        while (true) {
            consoleView.displayMessage("Welcome to Auto Driving Car Simulation!\n");
            boolean exit = gridController.start();
            if (exit) {
                consoleView.displayMessage("Thank you for running the simulation. Goodbye!");
                break;
            }
            consoleView.displayMessage("\n");
        }
        // closes the scanner
        consoleView.close();
    }

}