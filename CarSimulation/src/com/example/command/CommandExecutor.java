package com.example.command;

import com.example.model.Car;

public interface CommandExecutor {
    void execute(Car car, int step, int carIndex);

}
