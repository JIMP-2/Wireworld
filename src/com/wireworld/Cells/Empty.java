package com.wireworld.Cells;


import com.wireworld.GridView;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.Board;
import com.wireworld.model.CellState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Empty extends GridView {


  public Empty(BasicBoard initialBoard, int size, Stage primaryStage) {
    super(initialBoard, size, primaryStage);
  }

  public static void drawEmpty(Board simulationToDraw, int x, int y, GraphicsContext g) {

    g.setFill(Color.BLACK);
    g.fillRect(x, y, 1, 1);

  }
}




