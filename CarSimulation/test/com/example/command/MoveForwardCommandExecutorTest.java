package com.example.command;

import com.example.model.Car;
import com.example.model.Direction;
import com.example.model.Grid;
import org.junit.Test;


public class MoveForwardCommandExecutorTest {
    @Test
    public void testExecute_ValidMove() {
        // Create Grid 
        Grid grid = new Grid(10, 10);

        // Create a car
        Car car = new Car("bbb", 0, 0, Direction.N, "F");

        // Create MoveForwardCommandExecutor instance
        MoveForwardCommandExecutor executor = new MoveForwardCommandExecutor(grid);

        // Execute the command
        executor.execute(car, 1, 0);

        // Verify that the car's position is updated
        assert car.getCurrentX() == 0;  // Assuming car moved forward without collision
        assert car.getCurrentY() == 1;  // Assuming car moved forward without collision
    }

    @Test
    public void testExecute_OutOfBoundaryMove() {
        // Create Grid
        Grid grid = new Grid(2, 2);

        // Create a car
        Car car = new Car("bbb", 0, 0, Direction.N, "FFFFFFF");

        // Create MoveForwardCommandExecutor instance
        MoveForwardCommandExecutor executor = new MoveForwardCommandExecutor(grid);

        // Execute the command
        executor.execute(car, 1, 0);

        // Verify that the car's position is updated
        assert car.getCurrentX() == 0;  // Assuming car moved forward without collision
        assert car.getCurrentY() == 1;  // Assuming car moved forward without collision
    }

    @Test
    public void testExecute_CollisionMove() {
        // Create Grid
        Grid grid = new Grid(2, 2);

        // Create a car
        Car car = new Car("A", 0, 0, Direction.N, "FFFFFFF");
        Car car1 = new Car("B", 0, 0, Direction.N, "FFFFFFF");
        grid.addCar(car);
        grid.addCar(car1);
        // Create MoveForwardCommandExecutor instance
        MoveForwardCommandExecutor executor = new MoveForwardCommandExecutor(grid);

        // Execute the command for 1st car
        executor.execute(car, 1, 0);

        // Verify that the car's position is updated
        assert car.getCurrentX() == 0;  // Assuming car moved forward without collision
        assert car.getCurrentY() == 1;  // Assuming car moved forward without collision

        // Execute the command for 2nd car: collision happen here
        executor.execute(car1, 1, 1);

        // Verify that the car's position is updated
        assert grid.getIndices(0, 1).size() == 2;
        assert car1.getCurrentX() == 0;  // Assuming car moved forward with collision
        assert car1.getCurrentY() == 1;  // Assuming car moved forward with collision

    }

    @Test
    public void testExecute_CollisionMove_ThreeCars() {
        // Create Grid
        Grid grid = new Grid(2, 2);

        // Create a car
        Car car = new Car("A", 0, 0, Direction.N, "FFFFFFF");
        Car car1 = new Car("B", 0, 0, Direction.N, "FFFFFFF");
        Car car2 = new Car("C", 0, 0, Direction.N, "FFFFFFF");
        grid.addCar(car);
        grid.addCar(car1);
        grid.addCar(car2);
        // Create MoveForwardCommandExecutor instance
        MoveForwardCommandExecutor executor = new MoveForwardCommandExecutor(grid);

        // Execute the command for 1st car
        executor.execute(car, 1, 0);

        // Verify that the car's position is updated
        assert car.getCurrentX() == 0;  // Assuming car moved forward without collision
        assert car.getCurrentY() == 1;  // Assuming car moved forward without collision

        // Execute the command for 2nd car: collision happen here
        executor.execute(car1, 1, 1);
        // Execute the command for 3rd car: collision happen here
        executor.execute(car2, 1, 2);


        // check details for 2nd car
        assert car1.getCurrentX() == 0;  // Assuming car moved forward with collision
        assert car1.getCurrentY() == 1;  // Assuming car moved forward with collision

        //check details for 3rd car too
        assert car2.getCurrentX() == 0;  // Assuming car moved forward with collision
        assert car2.getCurrentY() == 1;

        assert grid.getIndices(0, 1).size() == 3;
    }

    @Test
    public void testExecute_CollisionAndNoCollisions_ThreeCars() {
        // Create Grid
        Grid grid = new Grid(100, 100);

        // Create a car
        Car car = new Car("A", 0, 0, Direction.N, "F");
        Car car1 = new Car("B", 0, 0, Direction.N, "F");
        Car car2 = new Car("C", 5, 0, Direction.N, "F");
        grid.addCar(car);
        grid.addCar(car1);
        grid.addCar(car2);
        // Create MoveForwardCommandExecutor instance
        MoveForwardCommandExecutor executor = new MoveForwardCommandExecutor(grid);

        // Execute the command for 1st car
        executor.execute(car, 1, 0);

        // Verify that the car's position is updated
        assert car.getCurrentX() == 0;  // Assuming car moved forward without collision
        assert car.getCurrentY() == 1;  // Assuming car moved forward without collision

        // Execute the command for 2nd car: collision happen here
        executor.execute(car1, 1, 1);
        // Execute the command for 3rd car: collision happen here

        executor.execute(car2, 1, 2);

        // check details for 2nd car
        assert car1.getCurrentX() == 0;  // Assuming car moved forward with collision
        assert car1.getCurrentY() == 1;  // Assuming car moved forward with collision

        //check details for 3rd car too : no collision
        assert car2.getCurrentX() == 5;
        assert car2.getCurrentY() == 1;

        assert grid.getIndices(0, 1).size() == 2;
        assert grid.getIndices(5, 1).size() == 1;
    }
}
