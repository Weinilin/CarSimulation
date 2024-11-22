package com.example.command;

import com.example.model.Car;
import com.example.model.Direction;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class RotateRightCommandExecutorTest {
    @Test
    public void testExecute() {
        // Create a car
        Car car = new Car("bb", 0, 0, Direction.N, "LR");

        // Create RotateRightCommandExecutor instance
        RotateRightCommandExecutor executor = new RotateRightCommandExecutor();

        // Execute the command
        executor.execute(car, 0, 0);

        // Verify that the car's direction is updated
        assert car.getCurrentDirection() == Direction.E;  // Assuming original direction was 'N'
    }
}

