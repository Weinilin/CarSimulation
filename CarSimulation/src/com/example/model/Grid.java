package com.example.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Grid {
    private int width;
    private int height;
    private List<Car> cars;
    private int maxCommandsLength;

    private HashMap<Point, List<Integer>> occupiedMap;


    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cars = new ArrayList<>();
        this.maxCommandsLength = 0;
        this.occupiedMap = new HashMap<>();
    }

    public void addMaxCommandsLength(int commandLen) {
        if (commandLen > maxCommandsLength) {
            maxCommandsLength = commandLen;
        }
    }

    public int getMaxCommandsLength() {
        return maxCommandsLength;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        this.cars.add(car);
        occupy(car.getCurrentX(), car.getCurrentY(), cars.size() - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Grid grid = (Grid) obj;
        return width == grid.width && height == grid.height && cars == grid.cars;
    }

    public HashMap<Point, List<Integer>> getGrid() {
        return occupiedMap;
    }

    public void resetCarsAfterSimulation() {
        occupiedMap = new HashMap<>();
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            car.resetCarAfterSimulation();
            occupy(car.getInitialX(), car.getInitialY(), i);
        }
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isCarNameUnique(String carName) {
        for (Car car : cars) {
            if (car.getCarName().equals(carName)) {
                return false; // Name already exists
            }
        }
        return true; // Name is unique
    }

    public boolean isOccupied(int x, int y) {
        return isValidPosition(x, y) && !getIndices(x, y).isEmpty();
    }
    public List<Integer> getIndices(int x, int y) {
        Point position = new Point(x, y);
        return occupiedMap.getOrDefault(position, Collections.emptyList());
    }
    public boolean occupy(int x, int y, int carIndex) {
        if (isValidPosition(x, y)) {
            Point position = new Point(x, y);
            occupiedMap.computeIfAbsent(position, k -> new ArrayList<>()).add(carIndex);
            return true;
        }
        return false;
    }

    public void clear(int x, int y, int carIndex) {
        if (isValidPosition(x, y)) {
            Point position = new Point(x, y);
            List<Integer> indices = occupiedMap.get(position);
            if (indices != null) {
                indices.remove(Integer.valueOf(carIndex));
                if (indices.isEmpty()) {
                     occupiedMap.remove(position);
                }
            }
        }
    }


}
