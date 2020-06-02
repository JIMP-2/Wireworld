package com.wireworld.cells;

import com.wireworld.GridView;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.Board;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Conductor extends GridView {


    public Conductor(BasicBoard initialBoard, int size, Stage primaryStage) throws IOException {
        super(initialBoard, size, primaryStage);
    }

    public static void drawConductor(Board simulationToDraw, int x, int y, GraphicsContext g) {

        g.setFill(Color.YELLOW);
        g.fillRect(x, y, 1, 1);

    }
}
