package com.example.command;

import com.example.model.Car;
import com.example.model.Command;

/*
 * Execute rotate left of a car in a gird of width x height.
 */
public class RotateLeftCommandExecutor implements CommandExecutor {

    public RotateLeftCommandExecutor() {
    }

    @Override
    public void execute(Car car, int step, int carIndex) {
        car.rotateLeft();
        car.addHistoryOfCommands(Command.L);

    }

}
