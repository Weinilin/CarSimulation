package com.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.model.Car;
import com.example.model.CollisionsDetail;
import com.example.model.Grid;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollisionControllerTest {

    @Test
    public void testHandleCollisionWithMultipleOccupants() {
        // Create mock objects for Grid and Car
        Grid grid = Mockito.mock(Grid.class);
        Car currentCar = Mockito.mock(Car.class);

        List<Car> cars = new ArrayList<>();
        // Populate the cars list with mock Car objects representing occupants
        cars.add(currentCar);
        cars.add(Mockito.mock(Car.class));
        cars.add(Mockito.mock(Car.class));
        //no collision details yet
        List<CollisionsDetail> collisionsDetails = new ArrayList<>();

        // Define test data
        int newX = 1;
        int newY = 1;
        int step = 1;
        // Stub the behavior of the Grid object to return the list of occupant indices
        Mockito.when(grid.getIndices(newX, newY)).thenReturn(Arrays.asList(0, 1, 2));
        Mockito.when(grid.getCars()).thenReturn(cars);
        Mockito.when(currentCar.getCollisionsDetails()).thenReturn(collisionsDetails);

        // Create an instance of CollisionController
        CollisionController collisionController = CollisionController.getInstance();

        // Call the instance method to handle collision
        collisionController.handleCollision(grid, 0, step, newX, newY);

        // Verify that the appropriate methods are called on the currentCar and occupantCar objects
        // Add your verification logic here
        verify(currentCar, times(2)).addCollisionsDetail(any());
    }

    @Test
    public void testHandleCollisionWithExistingOccupants() {
        // Create mock objects for Grid and Car
        Grid grid = Mockito.mock(Grid.class);
        Car currentCar = Mockito.mock(Car.class);
        Car car1 = Mockito.mock(Car.class);

        List<Car> cars = new ArrayList<>();
        // Populate the cars list with mock Car objects representing occupants
        cars.add(currentCar);
        cars.add(car1);
        cars.add(Mockito.mock(Car.class));
        List<CollisionsDetail> collisionsDetails = new ArrayList<>();
        //already contained 1 car collided in step 1
        collisionsDetails.add(new CollisionsDetail(1, car1));
        // Define test data
        int newX = 1;
        int newY = 1;
        int step = 2;
        // Stub the behavior of the Grid object to return the list of occupant indices
        Mockito.when(grid.getIndices(newX, newY)).thenReturn(Arrays.asList(0, 1, 2));
        Mockito.when(grid.getCars()).thenReturn(cars);
        Mockito.when(currentCar.getCollisionsDetails()).thenReturn(collisionsDetails);

        // Create an instance of CollisionController
        CollisionController collisionController = CollisionController.getInstance();

        // Call the instance method to handle collision
        collisionController.handleCollision(grid, 0, step, newX, newY);

        // Verify that the appropriate methods are called on the currentCar and occupantCar objects
        // Add your verification logic here
        verify(currentCar, times(1)).addCollisionsDetail(any());
    }
}

