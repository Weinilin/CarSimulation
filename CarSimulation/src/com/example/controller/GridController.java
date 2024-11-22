package com.example.controller;

import com.example.model.Car;
import com.example.model.Grid;
import com.example.view.ConsoleView;

/*
 * Control the setup of grid
 */
public class GridController {
    private Grid grid;
    private ConsoleView view;
    private CarController carController;

    public GridController(ConsoleView view) {
        this.view = view;
    }

    public void setCarController(CarController carController) {
        this.carController = carController;
    }

    public boolean start() {
        boolean isSuccess = setup();
        if (isSuccess)
            return startFirstOptions();

        return true;
    }

    public boolean setup() {
        while (true) {
            try {
                grid = view.readWidthAndHeight();
                break;
            } catch (Exception e) {
                view.displayMessage(e.getMessage());
            }
        }
        return true;
    }

    public boolean startFirstOptions() {
        carController = new CarController(grid, view);
        String nextChoice;
        while (true) {
            String choice = view.getOptions();
            if (choice.equals("1")) {
                handleAddCar();
            } else if (choice.equals("2")) {
                handleSimulation();
                nextChoice = view.getNextOptions();
                return !nextChoice.equals("1");
            }
        }
    }

    public void handleAddCar() {
        try {
            Car car = view.readAddCar();
            boolean isValidated = carController.isValidCarInput(car);
            if (isValidated) {
                carController.addCar(car);
            }
        } catch (Exception e) {
            view.displayMessage(e.getMessage());
        }
        view.printOutInitialPosition(grid.getCars());
    }

    public void handleSimulation() {
        carController.executeSimulation();
        view.printOutAllCarsResult(grid.getCars());
        grid.resetCarsAfterSimulation();
    }


    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
