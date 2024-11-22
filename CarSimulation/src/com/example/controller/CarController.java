package com.example.controller;

import com.example.model.Car;
import com.example.model.Grid;
import com.example.view.ConsoleView;

/*
 * Control the validation, addition and simulation of car
 */
public class CarController {
    private Grid grid;
    private ConsoleView view;

    public CarController(Grid grid, ConsoleView view) {
        this.grid = grid;
        this.view = view;
    }

    public boolean isValidCarInput(Car car) {
        boolean isValidPosition = grid.isValidPosition(car.getInitialX(), car.getInitialY());
        boolean isOccupied = grid.isOccupied(car.getInitialX(), car.getInitialY());
        boolean isCarNameUnique = grid.isCarNameUnique(car.getCarName());

        if (!isValidPosition) {
            view.displayMessage("Position is not within the range of 0 - width or 0 - height");
        } else if (isOccupied) {
            view.displayMessage("Another car occupies this position. Please choose another position.");
        } else if (!isCarNameUnique) {
            view.displayMessage("Car name '" + car.getCarName() + "' is not unique.");
        }
        return isValidPosition && !isOccupied && isCarNameUnique;
    }

    public void addCar(Car car) {
        grid.addCar(car);
        grid.addMaxCommandsLength(car.getCommands().length());
    }

    /*
     * method is to execute 1st command of all cars then 2nd command of all cars
     * until nth command of all cars has been executed
     */
    public void executeSimulation() {
        try {
            CommandController commandController = new CommandController(grid);
            for (int step = 0; step < grid.getMaxCommandsLength(); step++) {
                // Iterate through each car and execute its command if it has one at the current index
                for (int i = 0; i < grid.getCars().size(); i++) {
                    Car car = grid.getCars().get(i);
                    executeCarStep(commandController, step, i, car);
                }
                //after a step: check collisions
                checkCollisions(step);
            }
        } catch (IllegalArgumentException exception) {
            view.displayMessage(exception.getMessage());
        }
    }

    //check and handle collisions
    private void checkCollisions(int step) {
        for (int i = 0; i < grid.getCars().size(); i++) {
            Car car1 = grid.getCars().get(i);
            if (grid.isOccupied(car1.getCurrentX(), car1.getCurrentY())) {
                CollisionController.getInstance().handleCollision(grid, i, step, car1.getCurrentX(), car1.getCurrentY());
            }
        }
    }

    //execute a command
    private void executeCarStep(CommandController commandController, int currentStep, int carIndex, Car car) {
        String commands = car.getCommands();
        if (currentStep < commands.length() && !car.isCollided()) {
            char command = commands.charAt(currentStep);
            commandController.executeCommand(car, command, currentStep, carIndex);
        }
    }

}
