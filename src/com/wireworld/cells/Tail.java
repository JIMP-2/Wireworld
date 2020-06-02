package com.wireworld.cells;

import com.wireworld.GridView;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.Board;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tail extends GridView {


    public Tail(BasicBoard initialBoard, int size, Stage primaryStage) {
        super(initialBoard, size, primaryStage);
    }

    public static void drawTail(Board simulationToDraw, int x, int y, GraphicsContext g) {

        g.setFill(Color.RED);
        g.fillRect(x, y, 1, 1);

    }
}