package com.example.controller;

import com.example.model.Car;
import com.example.model.Direction;
import com.example.model.Grid;
import com.example.view.ConsoleView;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class CarControllerSimulationTest {

    @Before
    public void setup() {

    }
    @Test
    public void oneCarSimulationTest() {
        Scanner scanner = new Scanner(System.in);

        ConsoleView consoleView = new ConsoleView(scanner);
        Grid grid = new Grid(10, 10);
        CarController carController = new CarController(grid, consoleView);
        carController.addCar(new Car("carName",1, 2, Direction.N, "FFRFFFFRRL"));
        carController.executeSimulation();

        Car result = grid.getCars().get(0);
        assertEquals(5, result.getCurrentX());
        assertEquals(4, result.getCurrentY());
        assertEquals("S", result.getCurrentDirection().name());
    }

    @Test
    public void hasCollision_SimulationTest() {
        Scanner scanner = new Scanner(System.in);

        ConsoleView consoleView = new ConsoleView(scanner);
        Grid grid = new Grid(10, 10);
        CarController carController = new CarController(grid, consoleView);
        carController.addCar(new Car("carName1",1, 2, Direction.N, "FFRFFFFRRL"));
        carController.addCar(new Car("carName2",7, 8, Direction.W, "FFLFFFFFFF"));

        carController.executeSimulation();

        Car result = grid.getCars().get(0);
        assertTrue(result.isCollided());
        assertEquals("carName2", result.getCollisionsDetails().get(0).getCollidedWithCar().getCarName());
        assertEquals(5, result.getCurrentX());
        assertEquals(4, result.getCurrentY());
        assertEquals("E", result.getCurrentDirection().name());
        Car result1 = grid.getCars().get(1);
        assertTrue(result1.isCollided());
        assertEquals("carName1", result1.getCollisionsDetails().get(0).getCollidedWithCar().getCarName());
        assertEquals(5, result1.getCurrentX());
        assertEquals(4, result1.getCurrentY());
        assertEquals("S", result1.getCurrentDirection().name());
    }

    //has collision and without collision
    @Test
    public void hasMixed_SimulationTest() {
        Scanner scanner = new Scanner(System.in);

        ConsoleView consoleView = new ConsoleView(scanner);
        Grid grid = new Grid(1000, 1000);
        CarController carController = new CarController(grid, consoleView);
        carController.addCar(new Car("carName1",1, 2, Direction.N, "FFRFFFFRRL"));
        carController.addCar(new Car("carName2",7, 8, Direction.W, "FFLFFFFFFF"));
        carController.addCar(new Car("carName3",70, 80, Direction.W, "FFLFFFFFFF"));
        carController.addCar(new Car("carName4",700, 800, Direction.W, "FFLFFFFFFF"));

        carController.executeSimulation();

        Car result = grid.getCars().get(0);
        assertTrue(result.isCollided());
        assertEquals("carName2", result.getCollisionsDetails().get(0).getCollidedWithCar().getCarName());
        assertEquals(5, result.getCurrentX());
        assertEquals(4, result.getCurrentY());
        assertEquals("E", result.getCurrentDirection().name());

        Car result1 = grid.getCars().get(1);
        assertTrue(result1.isCollided());
        assertEquals("carName1", result1.getCollisionsDetails().get(0).getCollidedWithCar().getCarName());
        assertEquals(5, result1.getCurrentX());
        assertEquals(4, result1.getCurrentY());
        assertEquals("S", result1.getCurrentDirection().name());

        Car result2 = grid.getCars().get(2);
        assertEquals(68, result2.getCurrentX());
        assertEquals(73, result2.getCurrentY());
        assertEquals("S", result2.getCurrentDirection().name());

        Car result3 = grid.getCars().get(3);
        assertEquals(698, result3.getCurrentX());
        assertEquals(793, result3.getCurrentY());
        assertEquals("S", result3.getCurrentDirection().name());
    }

    @Test
    public void hasMixed_SimulationTest2() {
        Scanner scanner = new Scanner(System.in);

        ConsoleView consoleView = new ConsoleView(scanner);
        Grid grid = new Grid(10, 10);
        CarController carController = new CarController(grid, consoleView);
        carController.addCar(new Car("carName1",2, 1, Direction.N, "LLLFFFRRR"));
        carController.addCar(new Car("carName2",1, 1, Direction.E, "FFF"));
        carController.addCar(new Car("carName3",3, 3, Direction.E, "FFFR"));
        carController.addCar(new Car("carName4",5, 1, Direction.W, "FFFR"));
        carController.addCar(new Car("carName5",0, 0, Direction.E, "FFFF"));

        carController.executeSimulation();

        Car result = grid.getCars().get(2);
        assertFalse(result.isCollided());
        assertEquals(6, result.getCurrentX() );
        assertEquals(3, result.getCurrentY());
        assertEquals("S", result.getCurrentDirection().name());

        Car result1 = grid.getCars().get(0);
        assertEquals(2, result1.getCollisionsDetails().size());
        assertTrue(result1.isCollided());
        assertEquals("carName2", result1.getCollisionsDetails().get(0).getCollidedWithCar().getCarName());
        assertEquals(2, result1.getCollisionsDetails().get(0).getCollidedWithCar().getCurrentX());
        assertEquals(1, result1.getCollisionsDetails().get(0).getCollidedWithCar().getCurrentY());
        assertEquals(1, result1.getCollisionsDetails().get(0).getCollidedStep() );

        Car result2 = grid.getCars().get(1);
        assert result2.getCollisionsDetails().size() == 2;
        assertTrue(result2.isCollided());
        assertEquals("carName1", result2.getCollisionsDetails().get(0).getCollidedWithCar().getCarName());
        assertEquals(2, result2.getCollisionsDetails().get(0).getCollidedWithCar().getCurrentX());
        assertEquals(1, result2.getCollisionsDetails().get(0).getCollidedWithCar().getCurrentY());
        assertEquals(1, result2.getCollisionsDetails().get(0).getCollidedStep() );

        Car result3 = grid.getCars().get(3);
        assertTrue(result3.isCollided());
        assertEquals("carName1", result3.getCollisionsDetails().get(0).getCollidedWithCar().getCarName());
        assertEquals(2, result3.getCollisionsDetails().get(0).getCollidedWithCar().getCurrentX());
        assertEquals(1, result3.getCollisionsDetails().get(0).getCollidedWithCar().getCurrentY());
        assertEquals(3, result3.getCollisionsDetails().get(0).getCollidedStep());

        Car result4 = grid.getCars().get(4);
        assertFalse(result4.isCollided());
        assertEquals(4, result4.getCurrentX());
        assertEquals(0, result4.getCurrentY());
        assertEquals("E", result4.getCurrentDirection().name());
    }


    @Test
    public void noCollision_SimulationTest() {
        Scanner scanner = new Scanner(System.in);

        ConsoleView consoleView = new ConsoleView(scanner);
        Grid grid = new Grid(10, 10);
        CarController carController = new CarController(grid, consoleView);

        carController.addCar(new Car("carName1",1, 2, Direction.N, "FFRFFFFRRL"));
        carController.addCar(new Car("carName2",6, 8, Direction.W, "FFLFFFFFFF"));

        carController.executeSimulation();

        Car result = grid.getCars().get(0);
        assertFalse(result.isCollided());
    }

    @Test
    public void outOfBound_SimulationTest() {
        Scanner scanner = new Scanner(System.in);

        ConsoleView consoleView = new ConsoleView(scanner);
        Grid grid = new Grid(10, 10);
        CarController carController = new CarController(grid, consoleView);

        carController.addCar(new Car("carName1",0, 0, Direction.S, "FF"));

        carController.executeSimulation();

        Car result = grid.getCars().get(0);
        assertFalse(result.isCollided());
        //should not change data
        assertEquals(0, result.getCurrentX());
        assertEquals(0 , result.getCurrentY());

    }
}
