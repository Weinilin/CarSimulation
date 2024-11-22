package com.example.command;

import com.example.model.Car;
import com.example.model.Direction;
import com.example.model.Grid;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UndoCommandExecutorTest {
    @Test
    public void testExecute() {
        Grid grid = new Grid(10, 10);
        Car car = new Car("A", 0, 0, Direction.N, "FFFFU");
        grid.addCar(car);
        // Create RotateRightCommandExecutor instance
        UndoCommandExecutor executor = new UndoCommandExecutor(grid);

        // Execute the command
        executor.execute(car, 4, 0);

        // Verify that the car's direction is updated
        assertEquals( 3, car.getCurrentY());  // Assuming original direction was 'N'
        assert car.getCurrentX() == 0;
    }
}

