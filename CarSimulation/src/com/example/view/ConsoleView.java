package com.example.view;

import com.example.model.*;

import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/*
* Handle all the inputs and outputs
 */
public class ConsoleView {
    private Scanner scanner;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public Grid readWidthAndHeight() throws Exception {
        displayMessage("Please enter the width and height of the simulation field in x y format: ");
        int width = getInteger();
        int height = getInteger();
        validateDimensions(width, height);
        Grid grid = new Grid(width, height);
        displayMessage("\nYou have created a field of " + grid.getWidth() + " x " + grid.getHeight() + ".");
        return grid;
    }

    private void validateDimensions(int width, int height) throws Exception {
        // Check if there are more integers in the input
        if (!scanner.nextLine().isEmpty()) {
            throw new Exception("Invalid input. Pls provide only 2 values in x y format.");
        }
        if (width <= 0 || height <= 0) {
            throw new Exception("Invalid input. Width and height must be positive integers.");
        }
    }

    public String getNextOptions() {
        displayMessage("\nPlease choose from the following options:");
        displayMessage("[1] Start over");
        displayMessage("[2] Exit");

        return getSelection();
    }

    public String getOptions() {
        displayMessage("\nPlease choose from the following options:");
        displayMessage("[1] Add a car to field");
        displayMessage("[2] Run simulation");

        return getSelection();
    }

    private String getSelection() {
        String choice = scanner.nextLine();
        if(!choice.equals("1") && !choice.equals("2")) {
            displayMessage("Invalid selection. Please choose 1 or 2.");
        }
        return choice;
    }


    public void printOutInitialPosition(List<Car> cars) {
        if (!cars.isEmpty()) {
            displayMessage("\nYour current list of cars are:");
            for (Car car : cars) {
                displayMessage("- " + car.getCarName() + ", (" + car.getInitialX() + "," + car.getInitialY() + ") " + car.getInitialDirection().name() + ", " + car.getCommands());
            }
        }
    }

    public void printOutAllCarsResult(List<Car> cars) {
        if (!cars.isEmpty()) {
            printOutInitialPosition(cars);
            displayMessage("\nAfter simulation, the result is:");

            for (Car car : cars) {
                //if collision
                if (car.isCollided()) {
                    printOutCollisionDetail(car);
                } else {
                    displayMessage("- " + car.getCarName() + ", (" + car.getCurrentX() + "," + car.getCurrentY() + ") " + car.getCurrentDirection().name());
                }
            }
        }
    }

    private void printOutCollisionDetail(Car car) {
        StringJoiner joiner = new StringJoiner(", ");
        System.out.print("- " + car.getCarName() + ", collides with ");
        for (CollisionsDetail collisionsDetail : car.getCollisionsDetails()) {
            joiner.add(collisionsDetail.getCollidedWithCar().getCarName() + " at (" + collisionsDetail.getCollidedWithCar().getCurrentX() + "," + collisionsDetail.getCollidedWithCar().getCurrentY() + ") at step "
                    + collisionsDetail.getCollidedStep());
        }
        displayMessage(joiner.toString());
    }

    public Car readAddCar() throws Exception {
        String carName = getInput("\nPlease enter the name of the car:");
        if (carName.isEmpty()) throw new Exception("Invalid input. Car name is empty.");
        displayMessage("\nPlease enter initial position of the car " + carName + " in x y Direction format:");

        int xPosition = getInteger();
        int yPosition = getInteger();

        String directionString = scanner.nextLine().trim();

        Direction direction = getDirection(directionString);
        String commands = getCommands(carName);

        return new Car(carName, xPosition, yPosition, direction, commands);
    }

    private int getInteger() throws Exception {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.next();
            throw new Exception("Invalid input. Input must be integer");
        }
    }

    private Direction getDirection(String directionString) throws Exception {
        Direction direction = null;
        try {
            direction = Direction.valueOf(directionString.toUpperCase());
        } catch (Exception e) {
            throw new Exception("Direction '" + directionString + "' is invalid");
        }
        return direction;
    }

    private String getCommands(String carName) throws Exception {
        String commands = getInput("\nPlease enter the commands for car " + carName + ":");

        // Validate input
        validateCommands(commands);

        return commands.toUpperCase();
    }

    private void validateCommands(String commands) throws Exception {
        // Check if commands are empty
        if (commands.isEmpty()) {
            throw new Exception("Command is empty");
        }

        // Check if commands contain only valid characters
        for (char c : commands.toCharArray()) {
            if (!Command.contains(c)) {
                throw new Exception("Command '" + c + "' is invalid");
            }
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}




