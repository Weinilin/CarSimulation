package com.example.view;

import com.example.model.Car;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


public class AddCarConsoleViewTest {
    @Test
    public void testReadAddCar_ValidInput() throws Exception {
        // Prepare test input
        String input = "Car1\n1 2 N\nFRL\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);
        // Call the method
        ConsoleView consoleView = new ConsoleView(scanner);
        Car car = consoleView.readAddCar();

        assertEquals(car.getCurrentX(), 1);
        assertEquals(car.getCurrentY(), 2);
        assertEquals(car.getCarName(), "Car1");
        assertEquals(car.getCommands(), "FRL");
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        consoleView.printOutInitialPosition(cars);

        // Assert output
        assertTrue(outContent.toString().contains("- Car1, (1,2) N, FRL"));
//        assertEquals("Your current list of cars are:\n- Car1 (1,2) NORTH, FRL\n", outContent.toString());
    }

    @Test
    public void testReadAddCar_InvalidDirection() throws Exception {
        // Prepare test input with invalid direction
        String input = "Car2\n3 4 Z\nFRL\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);
        // Call the method
        ConsoleView consoleView = new ConsoleView(scanner);
        // Verify that adding the second car throws an exception
        Exception exception = assertThrows(Exception.class, consoleView::readAddCar);

        // Verify the exception message
        String expectedMessage = "Direction 'Z' is invalid";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Assert output
//        assertTrue( outContent.toString().contains("Direction 'Z' is invalid"));
    }

    @Test
    public void testReadAddCar_InvalidCommand() throws Exception {
        // Prepare test input with invalid command
        String input = "Car3\n5 6 N\nXYZ\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);
        // Call the method
        ConsoleView consoleView = new ConsoleView(scanner);

        // Verify that adding the second car throws an exception
        Exception exception = assertThrows(Exception.class, consoleView::readAddCar);

        // Verify the exception message
        String expectedMessage = "Command 'X' is invalid";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        // Assert output
//        assertTrue( outContent.toString().contains("Command 'X' is invalid"));
    }

    @Test
    public void testReadAddCar_InvalidEmptyCommand() throws Exception {
        // Prepare test input with invalid command
        String input = "Car3\n5 6 N\n\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);
        // Call the method
        ConsoleView consoleView = new ConsoleView(scanner);

        // Verify that adding the second car throws an exception
        Exception exception = assertThrows(Exception.class, consoleView::readAddCar);

        // Verify the exception message
        String expectedMessage = "Command is empty";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testReadAddCar_InvalidPosition() throws Exception {
        // Prepare test input with invalid command
        String input = "Car3\no 6 N\nFFFF\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);
        // Call the method
        ConsoleView consoleView = new ConsoleView(scanner);
        // Verify that adding the second car throws an exception
        Exception exception = assertThrows(Exception.class, consoleView::readAddCar);

        // Verify the exception message
        String expectedMessage = "Invalid input. Input must be integer";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

}

