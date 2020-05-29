package com.wireworld.Cells;

import com.wireworld.GridView;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.Board;
import com.wireworld.model.CellState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gate_or extends GridView {


    public Gate_or(BasicBoard initialBoard, int size, Stage primaryStage) {
        super(initialBoard, size, primaryStage);
    }

    public static void drawGateOr(Board simulationToDraw, int x, int y, GraphicsContext g) {

        g.setFill(Color.YELLOW);
        g.fillRect(x, y, 1, 1);
        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x+1, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x+2, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x+3, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x+4, y, CellState.CONDUCTOR);
        simulationToDraw.setState(x+5, y+1, CellState.CONDUCTOR);
        simulationToDraw.setState(x+5, y+2, CellState.CONDUCTOR);
        simulationToDraw.setState(x+4, y+2, CellState.CONDUCTOR);
        simulationToDraw.setState(x+6, y+2, CellState.CONDUCTOR);
        simulationToDraw.setState(x+7, y+2, CellState.CONDUCTOR);
        simulationToDraw.setState(x+8, y+2, CellState.CONDUCTOR);
        simulationToDraw.setState(x+5, y+3, CellState.CONDUCTOR);
        simulationToDraw.setState(x+4, y+4, CellState.CONDUCTOR);
        simulationToDraw.setState(x+3, y+4, CellState.CONDUCTOR);
        simulationToDraw.setState(x+2, y+4, CellState.CONDUCTOR);
        simulationToDraw.setState(x+1, y+4, CellState.CONDUCTOR);
        simulationToDraw.setState(x, y+4, CellState.CONDUCTOR);



    }
}