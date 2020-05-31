package com.wireworld;

import com.wireworld.model.CellState;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

import static com.wireworld.GridView.xRows;

public class Menu extends AnchorPane {
    private GridView gridView;
    private int generations;
    private TextField genField;
    private Stage primaryStage;


    private Simulator simulator;
    private Simulation simulation;


    public Menu(GridView gridView, Stage primaryStage, int size) {
        this.primaryStage = primaryStage;
        this.gridView = gridView;
        this.gridView.setDrawMode(CellState.CONDUCTOR);
        this.setPadding(new Insets(0, 27, 27, 0));



        Button drawConductor = new Button("conductor");
        drawConductor.setGraphic(new Circle(4, Color.YELLOW));
        drawConductor.setOnAction(this::handleDrawCONDUCTOR);
        drawConductor.setLayoutX(27);
        drawConductor.setLayoutY(27);
        drawConductor.setAlignment(Pos.CENTER);
        drawConductor.setPrefWidth(119);
        drawConductor.setPrefHeight(26);

        Button drawEmpty = new Button("empty");
        drawEmpty.setGraphic(new Circle(4, Color.BLACK));
        drawEmpty.setOnAction(this::handleDrawEMPTY);
        drawEmpty.setLayoutX(157);
        drawEmpty.setLayoutY(27);
        drawEmpty.setAlignment(Pos.CENTER);
        drawEmpty.setPrefWidth(119);
        drawEmpty.setPrefHeight(26);

        Button drawTail = new Button("tail");
        drawTail.setGraphic(new Circle(4, Color.RED));
        drawTail.setOnAction(this::handleDrawTAIL);
        drawTail.setLayoutX(157);
        drawTail.setLayoutY(64);
        drawTail.setAlignment(Pos.CENTER);
        drawTail.setPrefWidth(119);
        drawTail.setPrefHeight(26);


        Button drawHead = new Button("head");
        drawHead.setGraphic(new Circle(4, Color.BLUE));
        drawHead.setOnAction(this::handleDrawHED);
        drawHead.setLayoutX(27);
        drawHead.setLayoutY(64);
        drawHead.setAlignment(Pos.CENTER);
        drawHead.setPrefWidth(119);
        drawHead.setPrefHeight(26);


        Button drawOR = new Button("Gate_OR");
        drawOR.setOnAction(this::handleDrawOR);
        drawOR.setLayoutX(27);
        drawOR.setLayoutY(247);
        drawOR.setAlignment(Pos.CENTER);
        drawOR.setPrefWidth(74);
        drawOR.setPrefHeight(26);


        Button drawXOR = new Button("Gare_XOR");
        drawXOR.setOnAction(this::handleDrawXOR);
        drawXOR.setLayoutX(117);
        drawXOR.setLayoutY(247);
        drawXOR.setAlignment(Pos.CENTER);
        drawXOR.setPrefWidth(74);
        drawXOR.setPrefHeight(26);


        Button drawAND = new Button("Gate_AND");
        drawAND.setOnAction(this::handleDrawAND);
        drawAND.setLayoutX(206);
        drawAND.setLayoutY(247);
        drawAND.setAlignment(Pos.CENTER);
        drawAND.setPrefWidth(74);
        drawAND.setPrefHeight(26);


        Button drawDIODEL = new Button("Diode_LEFT");
        drawDIODEL.setOnAction(this::handleDrawDIODEL);
        drawDIODEL.setLayoutX(27);
        drawDIODEL.setLayoutY(286);
        drawDIODEL.setAlignment(Pos.CENTER);
        drawDIODEL.setPrefWidth(119);
        drawDIODEL.setPrefHeight(26);


        Button drawDIODELV = new Button("Diode_LEFT_V");
        drawDIODELV.setOnAction(this::handleDrawDIODELV);
        drawDIODELV.setLayoutX(161);
        drawDIODELV.setLayoutY(286);
        drawDIODELV.setAlignment(Pos.CENTER);
        drawDIODELV.setPrefWidth(119);
        drawDIODELV.setPrefHeight(26);


        Button drawDIODER = new Button("Diode_RIGHT");
        drawDIODER.setOnAction(this::handleDrawDIODER);
        drawDIODER.setLayoutX(27);
        drawDIODER.setLayoutY(326);
        drawDIODER.setAlignment(Pos.CENTER);
        drawDIODER.setPrefWidth(119);
        drawDIODER.setPrefHeight(26);


        Button drawDIODERV = new Button("Diode_RIGHT_V");
        drawDIODERV.setOnAction(this::handleDrawDIODERV);
        drawDIODERV.setLayoutX(161);
        drawDIODERV.setLayoutY(326);
        drawDIODERV.setAlignment(Pos.CENTER);
        drawDIODERV.setPrefWidth(119);
        drawDIODERV.setPrefHeight(26);

/*
        Label label = new Label("Liczba generacji: ");
        //   label.setTranslateY(238);
        label.setAlignment(Pos.BASELINE_LEFT);
        //  label.setTranslateX(xRows * size  -945);
        //    label.setPrefWidth(120);
*/

        genField = new TextField();
        genField.setPromptText("Liczba generacji:");
        genField.setLayoutX(27);
        genField.setLayoutY(116);
        genField.setAlignment(Pos.CENTER);
        genField.setPrefWidth(144);
        genField.setPrefHeight(26);



        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        start.setLayoutX(175);
        start.setLayoutY(116);
        start.setAlignment(Pos.CENTER);
        start.setPrefWidth(56);
        start.setPrefHeight(26);


        Button step = new Button(">>");
        step.setOnAction(this::handleStep);
        step.setLayoutX(241);
        step.setLayoutY(116);
        step.setAlignment(Pos.CENTER);

        Button start2 = new Button("Start_To_Infinity");
        start2.setOnAction(this::handleStart2);
        start2.setLayoutX(27);
        start2.setLayoutY(153);
        start2.setAlignment(Pos.CENTER);
        start2.setPrefWidth(247);
        start2.setPrefHeight(26);


        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        stop.setLayoutX(164);
        stop.setLayoutY(194);
        stop.setAlignment(Pos.CENTER);
        stop.setPrefWidth(113);
        stop.setPrefHeight(26);


        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        reset.setLayoutX(89);
        reset.setLayoutY(194);
        reset.setAlignment(Pos.CENTER);
        reset.setPrefWidth(56);
        reset.setPrefHeight(26);


        Button clean = new Button("clean");
        clean.setOnAction(this::handleClean);
        clean.setLayoutX(27);
        clean.setLayoutY(194);
        clean.setAlignment(Pos.CENTER);
        clean.setPrefWidth(56);
        clean.setPrefHeight(26);

        getChildren().addAll(drawConductor, drawEmpty, drawHead, drawTail, drawOR, drawXOR, drawAND, drawDIODEL, drawDIODER, drawDIODELV, drawDIODERV, genField, start, step, start2, stop, reset, clean);
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

    private void handleStop(ActionEvent actionEvent) {
        try {
            this.simulator.stop();
        } catch (Exception e){
            System.out.println("Stop Error");
        }
    }


    private void handleReset(ActionEvent actionEvent) {
        this.gridView.setApplicationState(GridView.EDITING);
        this.simulator = null;
        this.gridView.draw();
    }

    private void handleStart(ActionEvent actionEvent) {
        generations = Integer.parseInt(genField.getText());
        switchToSimulatingState();
        this.simulator.timelineNumber(generations);
        this.simulator.start();
    }


    private void handleStart2(ActionEvent actionEvent) {
        generations = 0;
        switchToSimulatingState();
        this.simulator.timelineInf();
        this.simulator.start();
    }


    private void handleStep(ActionEvent actionEvent) {

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

    private void handleClean(ActionEvent actionEvent) {

        this.gridView.setApplicationState(GridView.EDITING);
        this.simulator = null;
        this.gridView.drawClean();

    }

    }


