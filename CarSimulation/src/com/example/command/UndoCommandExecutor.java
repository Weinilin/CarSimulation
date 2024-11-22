package com.example.command;

import com.example.model.Car;
import com.example.model.CarPosition;
import com.example.model.Command;
import com.example.model.Grid;

public class UndoCommandExecutor implements CommandExecutor{
    private Grid grid;
    public UndoCommandExecutor(Grid grid) {
        this.grid = grid;

    }
    @Override
    public void execute(Car car, int step, int carIndex) {
        Command prevCommand = car.getPrevCommand();
        if(prevCommand != null) {
          switch(prevCommand) {
              case F:
                  CarPosition backwardPos = car.getMoveBackwardPosition();
                  int newX = backwardPos.getX();
                  int newY = backwardPos.getY();

                  if (!grid.isValidPosition(newX, newY)) {
                      return; // Exit early if the new position is invalid
                  }

                  // remove the car from previous position in grid.
                  grid.clear(car.getCurrentX(), car.getCurrentY(), carIndex);
                  car.move(newX, newY);
                  // the car occupy a new position in grid.
                  grid.occupy(newX, newY, carIndex);
                  break;
              case R:
                  car.rotateLeft();
                  break;
              case L:
                  car.rotateRight();
                  break;
          }
        }

    }
}
