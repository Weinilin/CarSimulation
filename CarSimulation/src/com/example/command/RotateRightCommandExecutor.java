package com.example.command;

import com.example.model.Car;
import com.example.model.Command;

/*
 * Execute rotate right of a car in a gird of width x height.
 */
public class RotateRightCommandExecutor implements CommandExecutor {

    public RotateRightCommandExecutor() {
    }

    @Override
    public void execute(Car car, int step, int carIndex) {
        car.rotateRight();
        car.addHistoryOfCommands(Command.R);

    }

}
