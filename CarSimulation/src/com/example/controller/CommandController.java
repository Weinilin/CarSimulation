package com.example.controller;

import com.example.command.*;
import com.example.model.Car;
import com.example.model.Grid;
/*
 * Control the command taken by the car
 */
public class CommandController {
    private Grid grid;

    public CommandController(Grid grid) {
        this.grid = grid;
    }

    public void executeCommand(Car car, char command, int step, int carIndex) {
        CommandExecutor executor = getCommandExecutor(command);

        executor.execute(car, step + 1, carIndex);
    }

    // set the right CommandExecutor based on the command
    private CommandExecutor getCommandExecutor(char command) {
        return switch (command) {
            case 'L' -> new RotateLeftCommandExecutor();
            case 'R' -> new RotateRightCommandExecutor();
            case 'F' -> new MoveForwardCommandExecutor(grid);
            case 'U' -> new UndoCommandExecutor(grid);
            default -> throw new IllegalArgumentException("Invalid command: " + command);
        };
    }
}
