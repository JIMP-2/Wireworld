package com.wireworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import com.wireworld.model.Board;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.CellState;
import com.wireworld.model.StandardRule;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class Toolbar extends ToolBar {

    private GridView gridView;
    private int generations;
    private TextField genField;



    private Simulator simulator;
    private Simulation simulation;

    public Toolbar(GridView gridView) {
        this.gridView = gridView;
        this.gridView.setDrawMode(CellState.CONDUCTOR);

        Button drawEmpty = new Button("empty");
        drawEmpty.setGraphic(new Circle(4, Color.BLACK));
        drawEmpty.setOnAction(this::handleDrawEMPTY);
        Button drawConductor = new Button("conductor");
        drawConductor.setGraphic(new Circle(4, Color.YELLOW));
        drawConductor.setOnAction(this::handleDrawCONDUCTOR);
        Button drawHead = new Button("head");
        drawHead.setGraphic(new Circle(4, Color.BLUE));
        drawHead.setOnAction(this::handleDrawHED);
        Button drawTail = new Button("tail");
        drawTail.setGraphic(new Circle(4, Color.RED));
        drawTail.setOnAction(this::handleDrawTAIL);

        Button clean = new Button("clean");
        clean.setOnAction(this::handleClean);
        Button step = new Button("Step");
        step.setOnAction((this::handleStep));
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button start2 = new Button("Start_To_Infinity");
        start2.setOnAction(this::handleStart2);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        Button drawOR = new Button("Gate_OR");
        drawOR.setOnAction(this::handleDrawOR);
        Button drawXOR = new Button("Gare_XOR");
        drawXOR.setOnAction(this::handleDrawXOR);
        Button drawAND = new Button("Gate_AND");
        drawAND.setOnAction(this::handleDrawAND);
        Button drawDIODEL = new Button("Diode_LEFT");
        drawDIODEL.setOnAction(this::handleDrawDIODEL);
        Button drawDIODELV = new Button("Diode_LEFT_V");
        drawDIODELV.setOnAction(this::handleDrawDIODELV);
        Button drawDIODER = new Button("Diode_RIGHT");
        drawDIODER.setOnAction(this::handleDrawDIODER);
        Button drawDIODERV = new Button("Diode_RIGHT_V");
        drawDIODERV.setOnAction(this::handleDrawDIODERV);

        genField = new TextField("20");
        Label label = new Label ("Liczba generacji: ");

        Button save = new Button("Save file");
        save.setOnAction(this::handleSave);

        this.getItems().addAll(drawConductor,drawHead,drawTail, drawEmpty, reset, step, label, genField, start,start2, stop, clean, save,drawAND, drawOR, drawXOR, drawDIODEL, drawDIODER, drawDIODELV, drawDIODERV);
    }

    private void handleSave(ActionEvent actionEvent) {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("generation.ser");
            ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(this.gridView.getInitialBoard());
            outStream.close();
            fileOut.close();
            System.out.println("Saved");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulator.stop();
    }




    private void handleReset(ActionEvent actionEvent) {
        this.gridView.setApplicationState(GridView.EDITING);
        this.simulator = null;
        this.gridView.draw();
    }

    private void handleStart(ActionEvent actionEvent) {
        generations = Integer.parseInt(genField.getText());
        switchToSimulatingState();
        this.simulator.start();
    }


    private void handleStart2(ActionEvent actionEvent) {
        generations = 0;
        switchToSimulatingState();
        this.simulator.start();
    }




        private void handleStep (ActionEvent actionEvent){

        switchToSimulatingState();
        this.gridView.getSimulation().step();
            this.gridView.draw();
            switchToSimulatingState();
            this.gridView.getSimulation().step();
            this.gridView.draw();

        }


    private void switchToSimulatingState() {
        if (this.gridView.getApplicationState() == GridView.EDITING) {
            this.gridView.setApplicationState(GridView.SIMULATING);
            this.simulator = new Simulator(this.gridView, this.gridView.getSimulation(), generations);
        }
    }



    private void handleDrawEMPTY(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.EMPTY);
    }
    private void handleDrawCONDUCTOR(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.CONDUCTOR);
    }
    private void handleDrawHED(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.HEAD);
    }
    private void handleDrawTAIL(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.TAIL);
    }

    private void handleDrawOR(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.GATE_OR);
    }


    private void handleDrawXOR(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.GATE_XOR);
    }

    private void handleDrawDIODEL(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.DIODE_LEFT);
    }

    private void handleDrawDIODELV(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.DIODE_LEFT_VERTICAL);
    }

    private void handleDrawDIODER(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.DIODE_RIGHT);
    }

    private void handleDrawDIODERV(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.DIODE_RIGHT_VERTICAL);
    }


    private void handleDrawAND(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.GATE_AND);
    }


    private void handleClean(ActionEvent actionEvent) {

        for (int x = 0; x < GridView.xRows; x++) {
            for (int y = 0; y < GridView.yColumns; y++) {
                this.gridView.clean(x,y);
            }
        }
    }


}