package com.example.command;

import com.example.model.Car;
import com.example.model.Direction;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class RotateLeftCommandExecutorTest {
    @Test
    public void testExecute() {
        // Create a car
        Car car = new Car("bbb", 0, 0, Direction.N, "LR");

        // Create RotateLeftCommandExecutor instance
        RotateLeftCommandExecutor executor = new RotateLeftCommandExecutor();

        // Execute the command
        executor.execute(car, 0, 0);

        // Verify that the car's direction is updated
        assert car.getCurrentPosition().getDirection() == Direction.W;  // Assuming original direction was 'N'
    }
}

