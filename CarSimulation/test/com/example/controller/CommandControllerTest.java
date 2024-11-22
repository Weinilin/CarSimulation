package com.example.controller;

import com.example.model.Car;
import com.example.model.Direction;
import com.example.model.Grid;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CommandControllerTest {
    @Test
    public void test_wrong_direction() {
        // Create  Grid
        Grid grid = new Grid(10, 9);

        Car car = new Car("name", 0, 1, Direction.S, "X invalid"); // Create a new car
        grid.addCar(car);
        CommandController controller = new CommandController(grid); // Create a new CommandController

        // Test for an invalid command
        assertThrows(IllegalArgumentException.class, () -> {
            controller.executeCommand(car, 'X', 0, 0);
        });
    }

    @Test
    public void test_F_ExecuteCommands() {
        // Create  Grid
        Grid grid = new Grid(10, 9);

        Car car = new Car("name", 0, 1, Direction.S, "F"); // Create a new car
        grid.addCar(car);
        CommandController controller = new CommandController(grid); // Create a new CommandController
        // Call the executeCommand() method with the 'F' command
        controller.executeCommand(car, 'F', 1000, 0);
        assertEquals(0, car.getCurrentX()); // Assert that the car's x-coordinate is as expected after executing commands
        assertEquals(0, car.getCurrentY()); // Assert that the car's y-coordinate is as expected after executing commands
        assertEquals(Direction.S, car.getCurrentDirection()); // Assert that the car's direction is as expected after executing commands
    }

    @Test
    public void test_R_ExecuteCommands() {
        // Create mock objects for Grid
        Grid grid = mock(Grid.class);

        Car car = new Car("name", 0, 1, Direction.S, "R"); // Create a new car
        CommandController controller = new CommandController(grid); // Create a new CommandController
        // Call the executeCommand() method with the 'R' command
        controller.executeCommand(car, 'R', 10000, 0);
        assertEquals(0, car.getCurrentX()); // Assert that the car's x-coordinate is as expected after executing commands
        assertEquals(1, car.getCurrentY()); // Assert that the car's y-coordinate is as expected after executing commands
        assertEquals(Direction.W, car.getCurrentDirection()); // Assert that the car's direction is as expected after executing commands
    }

    @Test
    public void test_L_ExecuteCommands() {
        // Create mock objects for Grid
        Grid grid = mock(Grid.class);

        Car car = new Car("name", 0, 1, Direction.W,  "L"); // Create a new car
        CommandController controller = new CommandController(grid); // Create a new CommandController
        // Call the executeCommand() method with the 'L' command
        controller.executeCommand(car, 'L', 100, 0);
        assertEquals(0, car.getCurrentX()); // Assert that the car's x-coordinate is as expected after executing commands
        assertEquals(1, car.getCurrentY()); // Assert that the car's y-coordinate is as expected after executing commands
        assertEquals(Direction.S, car.getCurrentDirection()); // Assert that the car's direction is as expected after executing commands
    }
}

