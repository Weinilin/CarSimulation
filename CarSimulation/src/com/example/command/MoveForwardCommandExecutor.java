package com.example.command;

import com.example.model.Car;
import com.example.model.CarPosition;
import com.example.model.Command;
import com.example.model.Grid;

/*
 * Execute the move forward of a car in a gird of width x height.
 * Updated the grid with the new position
 */
public class MoveForwardCommandExecutor implements CommandExecutor {
    private Grid grid;

    public MoveForwardCommandExecutor(Grid grid) {
        this.grid = grid;
    }

    @Override
    public void execute(Car car, int step, int carIndex) {
        CarPosition newCarPosition = car.getMoveForwardPosition();
        int newX = newCarPosition.getX();
        int newY = newCarPosition.getY();

        if (!grid.isValidPosition(newX, newY)) {
            return; // Exit early if the new position is invalid
        }

        // remove the car from previous position in grid.
        grid.clear(car.getCurrentX(), car.getCurrentY(), carIndex);
        car.move(newX, newY);
        // the car occupy a new position in grid.
        grid.occupy(newX, newY, carIndex);
        car.addHistoryOfCommands(Command.F);

    }

}



