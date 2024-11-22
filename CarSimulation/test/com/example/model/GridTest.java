package com.example.model;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GridTest {
    @Test
    public void testInvalidNegPosition() {
        Grid grid = new Grid(10, 10);

        assertFalse(grid.isValidPosition(-5, -4));
    }

    @Test
    public void testInvalidOutOfBoundPosition() {
        Grid grid = new Grid(10, 10);

        assertFalse(grid.isValidPosition(10, 1));
    }

    @Test
    public void testInvalidOutOfBoundPosition1() {
        Grid grid = new Grid(10, 10);

        assertFalse(grid.isValidPosition(1, 10));
    }

    @Test
    public void testValidPosition() {
        Grid grid = new Grid(10, 10);

        assertTrue(grid.isValidPosition(1, 9));
    }

    @Test
    public void testIsCarUnique() {
        Grid grid = new Grid(10, 10);
        Car car = new Car("B", 1, 1, Direction.N, "FFR");
        grid.addCar(car);

        assertFalse(grid.isCarNameUnique("B"));
    }

    @Test
    public void testIsCarNotUnique() {
        Grid grid = new Grid(10, 10);
        Car car = new Car("A", 1, 1, Direction.N, "FFR");
        grid.addCar(car);

        assertTrue(grid.isCarNameUnique("C"));
    }

    @Test
    public void testOccupyValidPosition() {
        Grid grid = new Grid(10, 10);
        assertTrue(grid.occupy(1, 1, 0)); // Assuming (1, 1) is a valid position
        // Now verify that the carIndex 0 is added to the occupiedMap at position (1, 1)
        assertTrue(grid.getGrid().containsKey(new Point(1, 1)));
        assertTrue(grid.getGrid().get(new Point(1, 1)).contains(0));
    }

    @Test
    public void testOccupyInvalidPosition() {
        Grid grid = new Grid(10, 10);
        assertFalse(grid.occupy(-1, -1, 0)); // Assuming (-1, -1) is an invalid position
        // Verify that the occupiedMap remains unchanged
        assertTrue(grid.getIndices(-1, -1).isEmpty());
    }

    @Test
    public void testAddCar() {
        Grid grid = new Grid(100, 10);
        // Create a test car
        Car car = new Car("TestCar", 1, 1, Direction.N, "");

        // Add the car
        grid.addCar(car);

        // Verify that the car is added to the list of cars
        assertTrue(grid.getCars().contains(car));

        // Verify that the corresponding position in the grid is occupied
        assertTrue(grid.isOccupied(1, 1));
    }

    @Test
    public void testResetCarsAfterSimulation() {
        Grid grid = new Grid(100, 10);

        // Create some test cars
        Car car1 = new Car("Car1", 1, 1, Direction.N, "");
        Car car2 = new Car("Car2", 2, 2, Direction.S, "");

        // Add the cars to the grid
        grid.addCar(car1);
        grid.addCar(car2);

        // Simulate some changes to the cars
        // (You might want to execute some commands or modify their positions)

        // Reset the cars after simulation
        grid.resetCarsAfterSimulation();

        // Verify that the grid is properly reset
        // Verify that the cars are correctly added to the grid and occupy their initial positions
        assertTrue(grid.isOccupied(2, 2)); // Car1 occupies initial position 0
        assertTrue(grid.isOccupied(1, 1)); // Car2 occupies initial position 1

        // Verify that the rest of the grid is not occupied
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                if (!(i == 2 && j == 2) && !(i == 1 && j == 1)) {
                    assertFalse(grid.isOccupied(i, j));
                }
            }
        }

        // Verify that each car is reset and placed back to its initial position
        assertEquals(1, car1.getCurrentX());
        assertEquals(1, car1.getCurrentY());
        assertEquals(Direction.N, car1.getCurrentDirection());

        assertEquals(2, car2.getCurrentX());
        assertEquals(2, car2.getCurrentY());
        assertEquals(Direction.S, car2.getCurrentDirection());
    }


}
