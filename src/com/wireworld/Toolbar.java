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
import javafx.util.Duration;


public class Toolbar extends ToolBar {

    private GridView gridView;
    private int generations;
    private TextField genField;



    private Simulator simulator;
    private Simulation simulation;

    public Toolbar(GridView gridView) {
        this.gridView = gridView;
        Button drawEmpty = new Button("empty");
        drawEmpty.setOnAction(this::handleDraw0);
        Button drawConductor = new Button("conductor");
        drawConductor.setOnAction(this::handleDraw1);
        Button drawHead = new Button("head");
        drawHead.setOnAction(this::handleDraw2);
        Button drawTail = new Button("tail");
        drawTail.setOnAction(this::handleDraw3);
        Button clean = new Button("clean");
        clean.setOnAction(this::handleClean);
        Button step = new Button("Step");
        step.setOnAction((this::handleStep));
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        Button start = new Button("Start");
        start.setOnAction(actionEvent -> {
            try {
                handleStart(actionEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        genField = new TextField("20");
        Label label = new Label ("Liczba generacji: ");

        this.getItems().addAll(drawEmpty, drawConductor,drawHead,drawTail, reset, step, label, genField, start, stop, clean);
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulator.stop();
    }

    private void handleStart(ActionEvent actionEvent) throws InterruptedException {
        generations = Integer.parseInt(genField.getText());
        Timeline pauseMaker = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchToSimulatingState();
                gridView.getSimulation().step();
                gridView.draw();
            }
        }));
        pauseMaker.setCycleCount(generations);
        pauseMaker.play();
        /*
        for(int i=0; i<generations; i++) {
            switchToSimulatingState();
            this.gridView.getSimulation().step();
            this.gridView.draw();
            //Thread.sleep(1000);
            System.out.println(i);


        }
*/
    }


    private void handleReset(ActionEvent actionEvent) {
        this.gridView.setApplicationState(GridView.EDITING);
        this.simulator = null;
        this.gridView.draw();
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
            this.simulator = new Simulator(this.gridView, this.gridView.getSimulation());
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