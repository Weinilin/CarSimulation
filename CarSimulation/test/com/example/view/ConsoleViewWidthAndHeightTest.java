package com.example.view;

import com.example.controller.GridController;
import com.example.model.Grid;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ConsoleViewWidthAndHeightTest {
    @Test
    public void testRead_Width_Height_Success() throws Exception {
        String input = "5 10\n"; // Simulated user input: width = 5, height = 10
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ConsoleView consoleView = new ConsoleView(scanner);

        Grid dimensions = consoleView.readWidthAndHeight();

        Grid expectedDimensions = new Grid(5, 10);
        assertEquals(expectedDimensions.getWidth(), dimensions.getWidth());
        assertEquals(expectedDimensions.getHeight(), dimensions.getHeight());
    }

    @Test
    public void testReadWidthHeight_InvalidInput() {
        String invalidInput = "5 invalidInput\n"; // Simulated user input: width = 5, height = 10
        InputStream in = new ByteArrayInputStream(invalidInput.getBytes());
        Scanner scanner = new Scanner(in);
        ConsoleView consoleView = new ConsoleView(scanner);

        Exception exception = assertThrows(Exception.class, consoleView::readWidthAndHeight);

        // Verify the exception message
        String expectedMessage = "Invalid input. Input must be integer";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testValidWidthAndHeightMsg() throws Exception {
        String input = "10 k\n10 10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);
        GridController gridController = new GridController(consoleView);

        gridController.setup();


        String result = "Please enter the width and height of the simulation field in x y format: "+
                "Invalid input. Input must be integer" +
                "Please enter the width and height of the simulation field in x y format: " +
                "You have created a field of 10 x 10.";
        assertEquals(result.replaceAll("\\r?\\n", ""), outContent.toString().replaceAll("\\r?\\n", ""));

        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));
    }
    @Test
    public void testNegValueMsg() throws Exception {
        String input = "10 -5\n10 10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);
        GridController gridController = new GridController(consoleView);

        gridController.setup();


        String result = "Please enter the width and height of the simulation field in x y format: "+
                "Invalid input. Width and height must be positive integers." +
                "Please enter the width and height of the simulation field in x y format: " +
                "You have created a field of 10 x 10.";
        assertEquals(result.replaceAll("\\r?\\n", ""), outContent.toString().replaceAll("\\r?\\n", ""));

        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));
    }

//    1
//    A 0, 1
//    b 0, 2
//    2
//    A 0, 2
//    B 0,1
    @Test
    public void testZeroValueMsg() throws Exception {
        String input = "0 10\n10 10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        Scanner scanner = new Scanner(in);

        ConsoleView consoleView = new ConsoleView(scanner);
        GridController gridController = new GridController(consoleView);

        gridController.setup();


        String result = "Please enter the width and height of the simulation field in x y format: "+
                "Invalid input. Width and height must be positive integers." +
                "Please enter the width and height of the simulation field in x y format: " +
                "You have created a field of 10 x 10.";
        assertEquals(result.replaceAll("\\r?\\n", ""), outContent.toString().replaceAll("\\r?\\n", ""));

        assertTrue(outContent.toString().replaceAll("\\r?\\n", "").contains(result));
    }



    @Test
    public void testReadWidthHeight_negativeValues_Failed() throws Exception {
        // Provide input with negative values
        String input = "-5 -10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ConsoleView consoleView = new ConsoleView(scanner);
        Exception exception = assertThrows(Exception.class, consoleView::readWidthAndHeight);

        // Verify the exception message
        String expectedMessage = "Invalid input. Width and height must be positive integers.";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testReadWidthHeight_zero_Failed() throws Exception {
        // Provide input with negative values
        String input = "0 10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ConsoleView consoleView = new ConsoleView(scanner);
        Exception exception = assertThrows(Exception.class, consoleView::readWidthAndHeight);

        // Verify the exception message
        String expectedMessage = "Invalid input. Width and height must be positive integers.";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testReadWidthAndHeight_multipleVal_Failed() throws Exception {
        // Provide input with negative values
        String input = "5 15 10\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        ConsoleView consoleView = new ConsoleView(scanner);

        // Assert that the method error
        Exception exception = assertThrows(Exception.class, consoleView::readWidthAndHeight);

        // Verify the exception message
        String expectedMessage = "Invalid input. Pls provide only 2 values in x y format.";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }


}
