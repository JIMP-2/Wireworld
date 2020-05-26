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
        drawEmpty.setOnAction(this::handleDraw0);
        Button drawConductor = new Button("conductor");
        drawConductor.setGraphic(new Circle(4, Color.YELLOW));
        drawConductor.setOnAction(this::handleDraw1);
        Button drawHead = new Button("head");
        drawHead.setGraphic(new Circle(4, Color.BLUE));
        drawHead.setOnAction(this::handleDraw2);
        Button drawTail = new Button("tail");
        drawTail.setGraphic(new Circle(4, Color.RED));
        drawTail.setOnAction(this::handleDraw3);
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

        genField = new TextField("20");
        Label label = new Label ("Liczba generacji: ");

        Button save = new Button("Save file");
        save.setOnAction(this::handleSave);

        this.getItems().addAll(drawConductor,drawHead,drawTail, drawEmpty, reset, step, label, genField, start,start2, stop, clean, save);
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



    private void handleDraw0(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.EMPTY);
    }
    private void handleDraw1(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.CONDUCTOR);
    }
    private void handleDraw2(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.HEAD);
    }
    private void handleDraw3(ActionEvent actionEvent) {

        this.gridView.setDrawMode(CellState.TAIL);
    }
    private void handleClean(ActionEvent actionEvent) {

        for (int x = 0; x < GridView.xRows; x++) {
            for (int y = 0; y < GridView.yColumns; y++) {
                this.gridView.clean(x,y);
            }
        }
    }


}