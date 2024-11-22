package com.example.model;

public class CollisionsDetail {
    private int collidedStep;
    private Car CollidedWithCar;

    public CollisionsDetail(int collidedStep, Car CollidedWithCar) {
        this.collidedStep = collidedStep;
        this.CollidedWithCar = CollidedWithCar;
    }

    public int getCollidedStep() {
        return collidedStep;
    }

    public void setCollidedStep(int collidedStep) {
        this.collidedStep = collidedStep;
    }

    public Car getCollidedWithCar() {
        return CollidedWithCar;
    }

    public void setCollidedWithCar(Car collidedWithCar) {
        this.CollidedWithCar = collidedWithCar;
    }

}
