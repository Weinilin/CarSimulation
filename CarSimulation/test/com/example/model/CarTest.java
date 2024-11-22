package com.example.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Stack;

public class CarTest {

    @Test
    public void testGetMoveForwardPosition_North() {
        // Arrange
        CarPosition currentPosition = new CarPosition(0, 0, Direction.N);
        Car car = new Car("Car", currentPosition.getX(), currentPosition.getY(), currentPosition.getDirection(), "");


        // Act
        CarPosition moveForwardPosition = car.getMoveForwardPosition();

        // Assert
        assertEquals(new CarPosition(0, 1, Direction.N), moveForwardPosition);
    }

    @Test
    public void testGetMoveBackwardPosition_North() {
        // Arrange
        CarPosition currentPosition = new CarPosition(0, 1, Direction.N);
        Car car = new Car("Car", currentPosition.getX(), currentPosition.getY(), currentPosition.getDirection(), "");


        // Act
        CarPosition moveBackwardPosition = car.getMoveBackwardPosition();

        // Assert
        assertEquals(new CarPosition(0, 0, Direction.N), moveBackwardPosition);
    }

    @Test
    public void testGetMoveForwardPosition_East() {
        // Arrange
        CarPosition currentPosition = new CarPosition(0, 0, Direction.E);
        Car car = new Car("Car", currentPosition.getX(), currentPosition.getY(), currentPosition.getDirection(), "");

        // Act
        CarPosition moveForwardPosition = car.getMoveForwardPosition();

        // Assert
        assertEquals(new CarPosition(1, 0, Direction.E), moveForwardPosition);
    }

    @Test
    public void testGetPrevCommandWithoutU() {
        // Arrange
        CarPosition currentPosition = new CarPosition(0, 0, Direction.E);
        Car car = new Car("Car", currentPosition.getX(), currentPosition.getY(), currentPosition.getDirection(), "FRLUU");
        car.addHistoryOfCommands(Command.F);
        car.addHistoryOfCommands(Command.R);
        car.addHistoryOfCommands(Command.L);

        // Act
        Command prevCommand = car.getPrevCommand();

        // Assert
        assertEquals(Command.L, prevCommand);

        assertEquals(Command.R, car.getPrevCommand());

        assertEquals(Command.F, car.getPrevCommand());
        assertEquals(null, car.getPrevCommand());
    }



    @Test
    public void testGetMoveForwardPosition_South() {
        // Arrange
        CarPosition currentPosition = new CarPosition(0, 1, Direction.S);
        Car car = new Car("Car", currentPosition.getX(), currentPosition.getY(), currentPosition.getDirection(), "");

        // Act
        CarPosition moveForwardPosition = car.getMoveForwardPosition();

        // Assert
        assertEquals(new CarPosition(0, 0, Direction.S), moveForwardPosition);
    }

    @Test
    public void testGetMoveForwardPosition_West() {
        // Arrange
        CarPosition currentPosition = new CarPosition(1, 1, Direction.W);
        Car car = new Car("Car", currentPosition.getX(), currentPosition.getY(), currentPosition.getDirection(), "");

        // Act
        CarPosition moveForwardPosition = car.getMoveForwardPosition();

        // Assert
        assertEquals(new CarPosition(0, 1, Direction.W), moveForwardPosition);
    }
}
