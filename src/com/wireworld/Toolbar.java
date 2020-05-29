package com.wireworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import com.wireworld.model.Board;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.CellState;
import com.wireworld.model.StandardRule;
import javafx.event.ActionEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

import static com.wireworld.GridView.xRows;


public class Toolbar extends ToolBar {

    private GridView gridView;
    private int generations;
    private TextField genField;
    private Stage primaryStage;




    private Simulator simulator;
    private Simulation simulation;

    public Toolbar(GridView gridView, Stage primaryStage, int size) {
        this.primaryStage = primaryStage;
        this.gridView = gridView;
        this.gridView.setDrawMode(CellState.CONDUCTOR);


        Button save = new Button("Save file");
        save.setOnAction(this::handleSave);
        save.setTranslateY(0);
        save.setAlignment(Pos.CENTER);
        save.setTranslateX(0);

        Button drawEmpty = new Button("empty");
        drawEmpty.setGraphic(new Circle(4, Color.BLACK));
        drawEmpty.setOnAction(this::handleDrawEMPTY);
        drawEmpty.setTranslateY(35);
        drawEmpty.setAlignment(Pos.CENTER);
        drawEmpty.setTranslateX(xRows*size-70);


        Button drawConductor = new Button("conductor");
        drawConductor.setGraphic(new Circle(4, Color.YELLOW));
        drawConductor.setOnAction(this::handleDrawCONDUCTOR);
        drawConductor.setTranslateY(35);
        drawConductor.setAlignment(Pos.CENTER);
        drawConductor.setTranslateX(xRows*size-70);




        Button drawHead = new Button("head");
        drawHead.setGraphic(new Circle(4, Color.BLUE));
        drawHead.setOnAction(this::handleDrawHED);
        drawHead.setTranslateY(65);
        drawHead.setAlignment(Pos.CENTER);
        drawHead.setTranslateX(xRows*size-228);


        Button drawTail = new Button("tail");
        drawTail.setGraphic(new Circle(4, Color.RED));
        drawTail.setOnAction(this::handleDrawTAIL);
        drawTail.setTranslateY(65);
        drawTail.setAlignment(Pos.CENTER);
        drawTail.setTranslateX(xRows*size-228);



        Button drawOR = new Button("Gate_OR");
        drawOR.setOnAction(this::handleDrawOR);
        drawOR.setTranslateY(105);
        drawOR.setAlignment(Pos.CENTER);
        drawOR.setTranslateX(xRows*size-341);




        Button drawXOR = new Button("Gare_XOR");
        drawXOR.setOnAction(this::handleDrawXOR);
        drawXOR.setTranslateY(105);
        drawXOR.setAlignment(Pos.CENTER);
        drawXOR.setTranslateX(xRows*size-341);





        Button drawAND = new Button("Gate_AND");
        drawAND.setOnAction(this::handleDrawAND);
        drawAND.setTranslateY(105);
        drawAND.setAlignment(Pos.CENTER);
        drawAND.setTranslateX(xRows*size-341);




        Button drawDIODEL = new Button("Diode_LEFT");
        drawDIODEL.setOnAction(this::handleDrawDIODEL);
        drawDIODEL.setTranslateY(135);
        drawDIODEL.setAlignment(Pos.CENTER);
        drawDIODEL.setTranslateX(xRows*size-556);




        Button drawDIODELV = new Button("Diode_LEFT_V");
        drawDIODELV.setOnAction(this::handleDrawDIODELV);
        drawDIODELV.setTranslateY(135);
        drawDIODELV.setAlignment(Pos.CENTER);
        drawDIODELV.setTranslateX(xRows*size-645);



        Button drawDIODER = new Button("Diode_RIGHT");
        drawDIODER.setOnAction(this::handleDrawDIODER);
        drawDIODER.setTranslateY(165);
        drawDIODER.setAlignment(Pos.CENTER);
        drawDIODER.setTranslateX(xRows*size-639);



        Button drawDIODERV = new Button("Diode_RIGHT_V");
        drawDIODERV.setOnAction(this::handleDrawDIODERV);
        drawDIODERV.setTranslateY(165);
        drawDIODERV.setAlignment(Pos.CENTER);
        drawDIODERV.setTranslateX(xRows*size-729);



        Label label = new Label ("Liczba generacji: ");
        label.setTranslateY(205);
        label.setAlignment(Pos.BASELINE_LEFT);
        label.setTranslateX(xRows*size-927);


        genField = new TextField("20");
        genField.setTranslateY(205);
        genField.setTranslateX(xRows*size-927);
        genField.setPrefWidth(80);




        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        start.setTranslateY(245);
        start.setAlignment(Pos.CENTER);
        start.setTranslateX(xRows*size-1115);





        Button start2 = new Button("Start_To_Infinity");
        start2.setOnAction(this::handleStart2);
        start2.setTranslateY(245);
        start2.setAlignment(Pos.CENTER);
        start2.setTranslateX(xRows*size-1115);




        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        stop.setTranslateY(275);
        stop.setAlignment(Pos.CENTER);
        stop.setTranslateX(xRows*size-1267);




        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        reset.setTranslateY(275);
        reset.setAlignment(Pos.CENTER);
        reset.setTranslateX(xRows*size-1267);



        Button clean = new Button("clean");
        clean.setOnAction(this::handleClean);
        clean.setTranslateY(275);
        clean.setAlignment(Pos.CENTER);
        clean.setTranslateX(xRows*size-1267);


        this.getItems().addAll( save, drawEmpty,  drawConductor, drawTail, drawHead,  drawOR, drawXOR, drawAND, drawDIODEL, drawDIODER, drawDIODELV, drawDIODERV, label, genField, start, start2, stop, reset , clean);

    }

    private void handleSave(ActionEvent actionEvent) {


            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
            fileChooser.getExtensionFilters().add(extFilter);

            File selectedFile = fileChooser.showSaveDialog(primaryStage);

            if(selectedFile != null) {
                if(!selectedFile.getName().contains(".ser")) {
                    selectedFile = new File(selectedFile.getAbsolutePath()+".ser");
                }
                try {
                    FileOutputStream fo = new FileOutputStream(selectedFile);
                    ObjectOutputStream oo = new ObjectOutputStream(fo);

                    oo.writeObject(this.gridView.getInitialBoard());

                    oo.close();
                    fo.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
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