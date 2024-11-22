package com.example.controller;

import com.example.model.Car;
import com.example.model.Direction;
import com.example.model.Grid;
import com.example.view.ConsoleView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CarControllerAddCarTest {

    private Grid grid;
    private ConsoleView view;
    private CarController carController;

    @Before
    public void setUp() {
        grid = Mockito.mock(Grid.class);
        view = Mockito.mock(ConsoleView.class);
        carController = new CarController(grid, view);
    }

    @Test
    public void testValidateCar_ValidPosition() {
        Car car = new Car("Car1", 1, 1, Direction.N, "");

        when(grid.isValidPosition(anyInt(), anyInt())).thenReturn(true);
        when(grid.isOccupied(anyInt(), anyInt())).thenReturn(false);
        when(grid.isCarNameUnique(anyString())).thenReturn(true);

        assertTrue(carController.isValidCarInput(car));
        verify(view, never()).displayMessage(anyString());
    }

    @Test
    public void testValidateCar_InvalidPosition() {
        Car car = new Car("Car1", -1, 1, Direction.N, "");

        when(grid.isValidPosition(anyInt(), anyInt())).thenReturn(false);

        assertFalse(carController.isValidCarInput(car));
        verify(view).displayMessage("Position is not within the range of 0 - width or 0 - height");
    }


    @Test
    public void testValidateCar_NoUniqueName() {
        Car car1 = new Car("B", 11, 11, Direction.N, "FFR");

        when(grid.isValidPosition(anyInt(), anyInt())).thenReturn(true);
        when(grid.isOccupied(anyInt(), anyInt())).thenReturn(false);
        when(grid.isCarNameUnique(anyString())).thenReturn(false); // Assume "B" already exists

        assertFalse(carController.isValidCarInput(car1));
        verify(view).displayMessage("Car name 'B' is not unique.");
    }

    @Test
    public void testValidateCar_allInValid() {
        Car car1 = new Car("B", 11, 11, Direction.N, "FFR");

        when(grid.isValidPosition(anyInt(), anyInt())).thenReturn(false);
        when(grid.isOccupied(anyInt(), anyInt())).thenReturn(true);
        when(grid.isCarNameUnique(anyString())).thenReturn(false); // Assume "B" already exists

        assertFalse(carController.isValidCarInput(car1));
    }

    @Test
    public void testValidateCar_SamePosition() {
        Car car1 = new Car("B", 11, 11, Direction.N, "FFR");

        when(grid.isValidPosition(anyInt(), anyInt())).thenReturn(true);
        when(grid.isOccupied(anyInt(), anyInt())).thenReturn(true);
        when(grid.isCarNameUnique(anyString())).thenReturn(true); // Assume "B" already exists

        assertFalse(carController.isValidCarInput(car1));
        verify(view).displayMessage("Another car occupies this position. Please choose another position.");
    }

    @Test
    public void testAddCar() {
        Car car = Mockito.mock(Car.class);
        when(car.getCommands()).thenReturn("FFR"); // Mocking behavior for getCommands()

        int initialCommandsLength = 3;

        carController.addCar(car);

        verify(grid).addCar(car);
        verify(grid).addMaxCommandsLength(initialCommandsLength);
    }

}

