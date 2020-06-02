package com.wireworld.cells;

import com.wireworld.GridView;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.Board;
import com.wireworld.model.CellState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Diode_left extends GridView {


    public Diode_left(BasicBoard initialBoard, int size, Stage primaryStage) {
        super(initialBoard, size, primaryStage);
    }

    public static void drawDiodeLeft(Board simulationToDraw, int x, int y, GraphicsContext g) {

        g.setFill(Color.YELLOW);
        g.fillRect(x, y, 1, 1);
        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 1, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 2, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 4, y - 1, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 4, y + 1, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 3, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 3, y - 1, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 3, y + 1, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 5, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 6, y, CellState.CONDUCTOR);

    }
}