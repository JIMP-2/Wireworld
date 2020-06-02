package com.wireworld.cells;

import com.wireworld.GridView;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.Board;
import com.wireworld.model.CellState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Diode_right_vertical extends GridView {


    public Diode_right_vertical(BasicBoard initialBoard, int size, Stage primaryStage) {
        super(initialBoard, size, primaryStage);
    }

    public static void drawDiodeRightVertical(Board simulationToDraw, int x, int y, GraphicsContext g) {

        g.setFill(Color.YELLOW);
        g.fillRect(x, y, 1, 1);
        g.fillRect(x - 1, y + 3, 1, 1);
        g.fillRect(x - 1, y + 4, 1, 1);
        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x, y + 1, CellState.CONDUCTOR);
        simulationToDraw.setState(x, y + 2, CellState.CONDUCTOR);
        simulationToDraw.setState(x - 1, y + 3, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 1, y + 3, CellState.CONDUCTOR);
        simulationToDraw.setState(x - 1, y + 4, CellState.CONDUCTOR);
        simulationToDraw.setState(x, y + 4, CellState.CONDUCTOR);
        simulationToDraw.setState(x + 1, y + 4, CellState.CONDUCTOR);
        simulationToDraw.setState(x, y + 5, CellState.CONDUCTOR);
        simulationToDraw.setState(x, y + 6, CellState.CONDUCTOR);
        simulationToDraw.setState(x, y + 7, CellState.CONDUCTOR);

    }

}


