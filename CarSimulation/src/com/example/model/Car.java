package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Car {
    private CarPosition initialPosition;
    private CarPosition currentPosition;
    private String commands;
    private String carName;

    private Stack<Command> historyOfCommands;
    private List<CollisionsDetail> collisionsDetails;

    public Car(String carName, int x, int y, Direction direction, String commands) {
        this.initialPosition = new CarPosition(x, y, direction);
        this.currentPosition = new CarPosition(x, y, direction);
        this.commands = commands;
        this.carName = carName;
        this.collisionsDetails = new ArrayList<>();
        this.historyOfCommands = new Stack<>();
    }

    public void resetCarAfterSimulation() {
        collisionsDetails = new ArrayList<>();
        currentPosition = new CarPosition(initialPosition.getX(), initialPosition.getY(), initialPosition.getDirection());
    }

    public int getInitialX() {
        return initialPosition.getX();
    }

    public void setInitialX(int initialX) {
        this.initialPosition.setX(initialX);
    }

    public int getInitialY() {
        return initialPosition.getY();
    }

    public void setInitialY(int initialY) {
        this.initialPosition.setY(initialY);
    }

    public Direction getInitialDirection() {
        return this.initialPosition.getDirection();
    }

    public void setInitialDirection(Direction direction) {
        this.initialPosition.setDirection(direction);
    }

    public Direction getCurrentDirection() {
        return this.currentPosition.getDirection();
    }

    public void setCurrentDirection(Direction direction) {
        this.currentPosition.setDirection(direction);
    }

    public String getCommands() {
        return commands;
    }


    public String getCarName() {
        return carName;
    }


    public int getCurrentX() {
        return this.currentPosition.getX();
    }

    public void setCurrentX(int currentX) {
        this.currentPosition.setX(currentX);
    }


    public int getCurrentY() {
        return this.currentPosition.getY();
    }


    public boolean isCollided() {
        return !collisionsDetails.isEmpty();
    }


    public CarPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCollisionsDetails(List<CollisionsDetail> collisionsDetails) {
        this.collisionsDetails = collisionsDetails;
    }

    public List<CollisionsDetail> getCollisionsDetails() {
        return collisionsDetails;
    }

    public void addCollisionsDetail(CollisionsDetail collisionsDetail) {
        collisionsDetails.add(collisionsDetail);
    }

    public void move(int newX, int newY) {
        currentPosition.setX(newX);
        currentPosition.setY(newY);
    }

    public void rotateLeft() {
        currentPosition.setDirection(currentPosition.getDirection().rotateLeft());
    }

    public void rotateRight() {
        currentPosition.setDirection(currentPosition.getDirection().rotateRight());
    }

    public CarPosition getMoveForwardPosition() {
        int newY = currentPosition.getY();
        int newX = currentPosition.getX();
        switch (currentPosition.getDirection()) {
            case N -> newY++;
            case E -> newX++;
            case S -> newY--;
            case W -> newX--;
        }

        return new CarPosition(newX, newY, currentPosition.getDirection());
    }

    public CarPosition getMoveBackwardPosition() {
        int newY = currentPosition.getY();
        int newX = currentPosition.getX();
        switch (currentPosition.getDirection()) {
            case N -> newY--;
            case E -> newX--;
            case S -> newY++;
            case W -> newX++;
        }

        return new CarPosition(newX, newY, currentPosition.getDirection());
    }

    public Command getPrevCommand() {
        if (!historyOfCommands.isEmpty())
           return historyOfCommands.pop();
        return null;
    }

    public Stack getHistoryOfCommands() {
        return historyOfCommands;
    }

    public void addHistoryOfCommands(Command command) {
        this.historyOfCommands.add(command);
    }

    public void setHistoryOfCommands(Stack historyOfCommands) {
        this.historyOfCommands = historyOfCommands;
    }
}
