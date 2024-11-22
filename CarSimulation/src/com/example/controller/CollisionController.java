package com.example.controller;

import com.example.model.Car;
import com.example.model.CollisionsDetail;
import com.example.model.Grid;

import java.util.List;
/*
 * Handle Collision
 */
public class CollisionController {
    private static CollisionController instance;

    // Private constructor to prevent external instantiation
    private CollisionController() {
    }

    public static CollisionController getInstance() {
        if (instance == null)
            instance = new CollisionController();
        return instance;
    }


    /**
     * This method
     * get a list of car indexes occupying the newX and newY
     * then add details of newly collided based on the indexes to the subject car
     * @param carIndex The index of the subject car
     * @param step     The current step that the car is at
     * @param newX     new x position
     * @param newY     new y position
     */
    public void handleCollision(Grid grid, int carIndex, int step, int newX, int newY) {
        List<Integer> listOfOccupantIndex = grid.getIndices(newX, newY);
        addNewlyCollidedCars(grid, carIndex, step, listOfOccupantIndex);
    }

    private void addNewlyCollidedCars(Grid grid, int carIndex, int step, List<Integer> listOfOccupantIndex) {
        Car currentCar = grid.getCars().get(carIndex);
        // those not added indexes should be at the end of list
        int startingIndex = currentCar.getCollisionsDetails().size() + 1;
        if (currentCar.getCollisionsDetails().isEmpty())
            startingIndex = 0;

        for (int i = startingIndex; i < listOfOccupantIndex.size(); i++) {
            int index = listOfOccupantIndex.get(i);
            Car occupantCar = grid.getCars().get(index);
            if (index != carIndex)
                currentCar.addCollisionsDetail(new CollisionsDetail(step + 1, occupantCar));
        }
    }

}
