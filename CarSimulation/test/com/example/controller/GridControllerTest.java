package com.example.controller;

import com.example.model.Car;
import com.example.model.Direction;
import com.example.model.Grid;
import com.example.view.ConsoleView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class GridControllerTest {
    private ConsoleView view;
    private GridController gridController;
    private CarController carController;

    @Before
    public void setUp() {
        view = mock(ConsoleView.class);
        gridController = new GridController(view);
        carController = mock(CarController.class);
        gridController.setCarController(carController);
    }

    @Test
    public void testSetup_Successful() throws Exception {
        when(view.readWidthAndHeight()).thenReturn(new Grid(5, 5));
        assertTrue(gridController.setup());
    }


    @Test
    public void testStart_InvalidOptionChosen() throws Exception {
        // Arrange
        when(view.readWidthAndHeight()).thenReturn(new Grid(10, 10));
        when(view.getOptions()).thenReturn("3", "2"); // Simulate user selecting an invalid option and then 2
        when(view.getNextOptions()).thenReturn("2");
        gridController.setup();
        // Act
        gridController.startFirstOptions();

        // Assert
        verify(view, times(2)).getOptions();  // Verify that getOptions() is called twice
    }

    @Test
    public void testStartFirstOptions_NextChoiceEquals2() throws Exception {
        // Mocking behavior of ConsoleView to return "2" as the run simulation and "2" to exit
        when(view.readWidthAndHeight()).thenReturn(new Grid(10, 10));
        when(view.getOptions()).thenReturn("2");
        when(view.getNextOptions()).thenReturn("2");

        gridController.setup();
        assertTrue(gridController.startFirstOptions());
    }

    @Test
    public void testHandleAddCar_ValidInput() throws Exception {
        when(view.readWidthAndHeight()).thenReturn(new Grid(10, 10));
        gridController.setup();

        // Mocking behavior of ConsoleView to return valid input for adding a car
        when(view.readAddCar()).thenReturn(new Car("Car1", 1, 1, Direction.N, "FFRL"));
        when(carController.isValidCarInput(any(Car.class))).thenReturn(true);

        // Calling the method to be tested
        gridController.handleAddCar();

        // Verifying that the carController's addCar method was called
        verify(carController, times(1)).addCar(any(Car.class));
    }

    @Test
    public void testHandleAddCar_InvalidInput() throws Exception {
        // Mocking behavior of ConsoleView to return invalid input for adding a car
        when(view.readAddCar()).thenThrow(new Exception("Invalid input"));
        when(view.readWidthAndHeight()).thenReturn(new Grid(10, 10));
        gridController.setup();
        // Calling the method to be tested
        gridController.handleAddCar();

        // Verifying that the error message was displayed
        verify(view, times(1)).displayMessage("Invalid input");
        // Verifying that the carController's addCar method was not called
        verify(carController, never()).addCar(any(Car.class));
    }

    @Test
    public void testHandleSimulation() throws Exception {
        when(view.readWidthAndHeight()).thenReturn(new Grid(10, 10));
        gridController.setup();
        List<Car> cars = new ArrayList<>();
        when(view.getOptions()).thenReturn("2");
        gridController.handleSimulation();
        verify(view, times(1)).printOutAllCarsResult(cars);
    }

    @Test
    public void testValidateCar_SamePosition() {
        String input = "10 10\n1\nCar1\n1 2 N\nFRL\n1\nCar1\n1 2 N\nFRL\n2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        assertTrue(outContent.toString().contains("- Car1, (1,2) N, FRL"));
        assertTrue(outContent.toString().contains("Another car occupies this position. Please choose another position."));

    }

    @Test
    public void testValidWithoutCollision() {
        String input = "10 10\n1\nCar1\n1 2 N\nFRL\n1\nCar2\n5 2 S\nFRL\n2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        String result = "Your current list of cars are:" +
                "- Car1, (1,2) N, FRL" +
                "- Car2, (5,2) S, FRL" +
                "After simulation, the result is:" +
                "- Car1, (1,3) N" +
                "- Car2, (5,1) S";
        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));

    }

    @Test
    public void testValidWithCollision() {
        String input = "10 10\n1\nCar1\n1 2 N\nFFRFFFFRRL\n1\nCar2\n7 8 W\nFFLFFFFFFF\n2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        String result = "Your current list of cars are:" +
                "- Car1, (1,2) N, FFRFFFFRRL" +
                "- Car2, (7,8) W, FFLFFFFFFF" +
                "After simulation, the result is:" +
                "- Car1, collides with Car2 at (5,4) at step 7" +
                "- Car2, collides with Car1 at (5,4) at step 7";
        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));

    }

    @Test
    public void testValidWithThreeCollisions() {
        String input = "10 10\n1\nCar1\n1 2 N\nFFRFFFFRRL\n1\nCar2\n7 8 W\nFFLFFFFFFF\n1\nCar3\n5 8 S\nFFFFL\n2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        String result = "Your current list of cars are:" +
                "- Car1, (1,2) N, FFRFFFFRRL" +
                "- Car2, (7,8) W, FFLFFFFFFF" +
                "- Car3, (5,8) S, FFFFL" +
                "After simulation, the result is:" +
                "- Car1, collides with Car3 at (5,4) at step 7, Car2 at (5,4) at step 7" +
                "- Car2, collides with Car3 at (5,4) at step 7, Car1 at (5,4) at step 7" +
                "- Car3, collides with Car1 at (5,4) at step 7, Car2 at (5,4) at step 7";
        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));

    }


    @Test
    public void testValidWithMixedCollisions() {
        String input = "10 10\n1\nCar1\n1 2 N\nFFRFFFFRRL\n1\nCar4\n1 1 E\nFFFRFFFL\n1\nCar2\n7 8 W\nFFLFFFFFFF\n1\nCar3\n5 8 S\nFFFFL\n2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        String result = "Your current list of cars are:" +
                "- Car1, (1,2) N, FFRFFFFRRL" +
                "- Car4, (1,1) E, FFFRFFFL" +
                "- Car2, (7,8) W, FFLFFFFFFF" +
                "- Car3, (5,8) S, FFFFL" +
                "After simulation, the result is:" +
                "- Car1, collides with Car3 at (5,4) at step 7, Car2 at (5,4) at step 7" +
                "- Car4, (4,0) E" +
                "- Car2, collides with Car3 at (5,4) at step 7, Car1 at (5,4) at step 7" +
                "- Car3, collides with Car1 at (5,4) at step 7, Car2 at (5,4) at step 7";
        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));

    }
    @Test
    public void testEdgeCase() {
        String input = "1000 1000\n1\nCar1\n0 0 E\nFFF\n1\nCar4\n2 0 S\nRFF\n2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        String result = "Your current list of cars are:" +
                "- Car1, (0,0) E, FFF" +
                "- Car4, (2,0) S, RFF" +
                "After simulation, the result is:" +
                "- Car1, (3,0) E" +
                "- Car4, (0,0) W";
        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));
    }

    @Test
    public void testValidWithMaxLen() {
        String input = "1000 1000\n1\nCar1\n1 1 N\nFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFRFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF\n1\nCar4\n99 99 S\nFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFLFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF\n2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        String result = "Your current list of cars are:" +
                "- Car1, (1,1) N, FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFRFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "- Car4, (99,99) S, FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFLFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "After simulation, the result is:" +
                "- Car1, (67,72) E" +
                "- Car4, (176,41) E";
        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));

    }
    @Test
    public void testValidWith_TwentyoneCars_outOfBound_MultipleCollisions() {
        String input = "6 6\n1\nCar1\n1 1 w\nfffffrrrll\n1\nCar2\n3 3 n\nfffffffrl\n" +
                "\n1\nCar3\n1 0 w\nffffffrrr\n" +
                "\n1\nCar4\n0 1 e\nrrrrrrrrffffl\n" +
                "\n1\nCar5\n2 2 n\nffffffrrrrlll\n" +
                "\n1\nCar6\n3 0 n\nffffffrrrrrl\n" +
                "\n1\nCar7\n0 0 E\nfffffrrrrrrflffffr\n" +
                "\n1\nCar8\n5 5 N\nfffffffffrrrrlll\n" +
                "\n1\nCar9\n3 2 N\nrrrrrfffrrrll\n" +
                "\n1\nCar10\n4 5 e\nffffffrrrrrlll\n" +
                "\n1\nCar11\n1 5 w\nllllrrrffff\n" +
                "\n1\nCar12\n4 1 S\nfrlf\n" +
                "\n1\nCar13\n5 1 E\nffffffffffffrrrrrrrrrrffffffffllllllll\n" +
                "\n1\nCar14\n1 2 E\nfffrrrll\n" +
                "\n1\nCar15\n1 1 W\nf\n" +
                "\n1\nCar16\n4 4 S\nrrffll\n" +
                "\n1\nCar17\n4 2 S\nrffffl\n" +
                "\n1\nCar18\n3 1 W\nffffrrrrrr\n" +
                "\n1\nCar19\n5 3 E\nrffffffl\n" +
                "\n1\nCar20\n3 4 E\nfffffrrr\n" +
                "\n1\nCar21\n4 3 s\nffffffflll\n" +
                "2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        String result = "Your current list of cars are:" +
                "- Car1, (1,1) W, FFFFFRRRLL" +
                "- Car2, (3,3) N, FFFFFFFRL" +
                "- Car3, (1,0) W, FFFFFFRRR" +
                "- Car4, (0,1) E, RRRRRRRRFFFFL" +
                "- Car5, (2,2) N, FFFFFFRRRRLLL" +
                "- Car6, (3,0) N, FFFFFFRRRRRL" +
                "- Car7, (0,0) E, FFFFFRRRRRRFLFFFFR" +
                "- Car8, (5,5) N, FFFFFFFFFRRRRLLL" +
                "- Car9, (3,2) N, RRRRRFFFRRRLL" +
                "- Car10, (4,5) E, FFFFFFRRRRRLLL" +
                "- Car11, (1,5) W, LLLLRRRFFFF" +
                "- Car12, (4,1) S, FRLF" +
                "- Car13, (5,1) E, FFFFFFFFFFFFRRRRRRRRRRFFFFFFFFLLLLLLLL" +
                "- Car14, (1,2) E, FFFRRRLL" +
                "- Car16, (4,4) S, RRFFLL" +
                "- Car17, (4,2) S, RFFFFL" +
                "- Car18, (3,1) W, FFFFRRRRRR" +
                "- Car19, (5,3) E, RFFFFFFL" +
                "- Car20, (3,4) E, FFFFFRRR" +
                "- Car21, (4,3) S, FFFFFFFLLL" +
                "After simulation, the result is:" +
                "- Car1, collides with Car4 at (0,1) at step 1, Car18 at (0,1) at step 3" +
                "- Car2, (3,5) N" +
                "- Car3, (0,0) S" +
                "- Car4, collides with Car1 at (0,1) at step 1, Car18 at (0,1) at step 3" +
                "- Car5, (2,5) E" +
                "- Car6, collides with Car9 at (3,2) at step 2, Car14 at (3,2) at step 2" +
                "- Car7, collides with Car12 at (4,0) at step 4" +
                "- Car8, collides with Car10 at (5,5) at step 1" +
                "- Car9, collides with Car6 at (3,2) at step 2, Car14 at (3,2) at step 2" +
                "- Car10, collides with Car8 at (5,5) at step 1" +
                "- Car11, (1,1) S" +
                "- Car12, collides with Car7 at (4,0) at step 4" +
                "- Car13, collides with Car19 at (5,1) at step 3" +
                "- Car14, collides with Car9 at (3,2) at step 2, Car6 at (3,2) at step 2" +
                "- Car16, collides with Car20 at (4,4) at step 1" +
                "- Car17, collides with Car21 at (4,2) at step 1" +
                "- Car18, collides with Car4 at (0,1) at step 3, Car1 at (0,1) at step 3" +
                "- Car19, collides with Car13 at (5,1) at step 3" +
                "- Car20, collides with Car16 at (4,4) at step 1" +
                "- Car21, collides with Car17 at (4,2) at step 1";
        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));
    }

    @Test
    public void testValidWithTwentyCars_threeCollisionsPerCar() {
        String input = "6 6\n1\nCar1\n1 1 w\nfffffrrrll\n1\nCar2\n3 3 n\nfffffffrl\n" +
                "\n1\nCar3\n1 0 w\nffffffrrr\n" +
                "\n1\nCar4\n0 1 e\nrrrrrrrrffffl\n" +
                "\n1\nCar5\n2 2 n\nffffffrrrrlll\n" +
                "\n1\nCar6\n3 0 n\nffffffrrrrrl\n" +
                "\n1\nCar7\n0 0 E\nfffffrrrrrrflffffr\n" +
                "\n1\nCar8\n5 5 N\nfffffffffrrrrlll\n" +
                "\n1\nCar9\n3 2 N\nrrrrrfffrrrll\n" +
                "\n1\nCar10\n4 5 e\nffffffrrrrrlll\n" +
                "\n1\nCar11\n1 5 s\nffffrffff\n" +
                "\n1\nCar13\n5 1 E\nffffffffffffrrrrrrrrrrffffffffllllllll\n" +
                "\n1\nCar14\n1 2 E\nfffrrrll\n" +
                "\n1\nCar15\n1 1 W\nf\n" +
                "\n1\nCar16\n4 4 S\nrrffll\n" +
                "\n1\nCar17\n4 2 S\nrffffl\n" +
                "\n1\nCar18\n3 1 W\nffffrrrrrr\n" +
                "\n1\nCar19\n5 3 E\nrffffffl\n" +
                "\n1\nCar20\n3 4 E\nfffffrrr\n" +
                "\n1\nCar21\n4 3 s\nffffffflll\n" +
                "2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);

        GridController gridController1 = new GridController(consoleView);

        gridController1.start();

        String result = "Your current list of cars are:" +
                "- Car1, (1,1) W, FFFFFRRRLL" +
                "- Car2, (3,3) N, FFFFFFFRL" +
                "- Car3, (1,0) W, FFFFFFRRR" +
                "- Car4, (0,1) E, RRRRRRRRFFFFL" +
                "- Car5, (2,2) N, FFFFFFRRRRLLL" +
                "- Car6, (3,0) N, FFFFFFRRRRRL" +
                "- Car7, (0,0) E, FFFFFRRRRRRFLFFFFR" +
                "- Car8, (5,5) N, FFFFFFFFFRRRRLLL" +
                "- Car9, (3,2) N, RRRRRFFFRRRLL" +
                "- Car10, (4,5) E, FFFFFFRRRRRLLL" +
                "- Car11, (1,5) S, FFFFRFFFF" +
                "- Car13, (5,1) E, FFFFFFFFFFFFRRRRRRRRRRFFFFFFFFLLLLLLLL" +
                "- Car14, (1,2) E, FFFRRRLL" +
                "- Car16, (4,4) S, RRFFLL" +
                "- Car17, (4,2) S, RFFFFL" +
                "- Car18, (3,1) W, FFFFRRRRRR" +
                "- Car19, (5,3) E, RFFFFFFL" +
                "- Car20, (3,4) E, FFFFFRRR" +
                "- Car21, (4,3) S, FFFFFFFLLL" +
                "After simulation, the result is:" +
                "- Car1, collides with Car4 at (0,1) at step 1, Car18 at (0,1) at step 3, Car11 at (0,1) at step 6" +
                "- Car2, (3,5) N" +
                "- Car3, (0,0) S" +
                "- Car4, collides with Car1 at (0,1) at step 1, Car18 at (0,1) at step 3, Car11 at (0,1) at step 6" +
                "- Car5, (2,5) E" +
                "- Car6, collides with Car9 at (3,2) at step 2, Car14 at (3,2) at step 2" +
                "- Car7, (4,0) W" +
                "- Car8, collides with Car10 at (5,5) at step 1" +
                "- Car9, collides with Car6 at (3,2) at step 2, Car14 at (3,2) at step 2" +
                "- Car10, collides with Car8 at (5,5) at step 1" +
                "- Car11, collides with Car4 at (0,1) at step 6, Car1 at (0,1) at step 6, Car18 at (0,1) at step 6" +
                "- Car13, collides with Car19 at (5,1) at step 3" +
                "- Car14, collides with Car9 at (3,2) at step 2, Car6 at (3,2) at step 2" +
                "- Car16, collides with Car20 at (4,4) at step 1" +
                "- Car17, collides with Car21 at (4,2) at step 1" +
                "- Car18, collides with Car4 at (0,1) at step 3, Car1 at (0,1) at step 3, Car11 at (0,1) at step 6" +
                "- Car19, collides with Car13 at (5,1) at step 3" +
                "- Car20, collides with Car16 at (4,4) at step 1" +
                "- Car21, collides with Car17 at (4,2) at step 1";
        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));
    }

}
