package com.wireworld.Cells;

import com.wireworld.GridView;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.Board;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Head extends GridView {


    public Head(BasicBoard initialBoard, int size, Stage primaryStage) {
        super(initialBoard, size, primaryStage);
    }

    public static void drawHead(Board simulationToDraw, int x, int y, GraphicsContext g) {

        g.setFill(Color.BLUE);
        g.fillRect(x, y, 1, 1);

    }
}