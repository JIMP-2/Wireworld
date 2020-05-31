package com.wireworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import static com.wireworld.GridView.yColumns;
/* TO JEST STARE
potem pewnie bedzie trzeba wywalic, na razie zostawilam, jakby w keylistenerach mi sie cos pomieszalo
 */

public class MainTool extends HBox {

    private GridView gridView;
    private int generations;
    private TextField genField;
    private Stage primaryStage;


    private Simulator simulator;
    private Simulation simulation;

    public MainTool(GridView gridView, Stage primaryStage, int size) {
        this.primaryStage = primaryStage;
        this.gridView = gridView;
        this.gridView.setDrawMode(CellState.CONDUCTOR);


        HBox vb = new HBox();
       // vb.setPadding(new Insets(10, 50, 50, 50));
        //
        vb.setSpacing(10);

        Label label1 = new Label("The Wireworld Computer ");
       // label1.setTranslateY(0);
        label1.setAlignment(Pos.BASELINE_LEFT);
       // label1.setTranslateX(0);



        Button drawEmpty = new Button("empty");
        drawEmpty.setGraphic(new Circle(4, Color.BLACK));
        drawEmpty.setOnAction(this::handleDrawEMPTY);
       //drawEmpty.setTranslateY(28);
        drawEmpty.setAlignment(Pos.CENTER);
      //  drawEmpty.setPrefWidth(120);

        //drawEmpty.setTranslateX(xRows * size -130 );



        Button drawConductor = new Button("conductor");
        drawConductor.setGraphic(new Circle(4, Color.YELLOW));
        drawConductor.setOnAction(this::handleDrawCONDUCTOR);
       // drawConductor.setTranslateY(28);
        drawConductor.setAlignment(Pos.CENTER);
       // drawConductor.setPrefWidth(140);
       // drawConductor.setTranslateX(xRows * size -120);




        Button drawTail = new Button("tail");
        drawTail.setGraphic(new Circle(4, Color.RED));
        drawTail.setOnAction(this::handleDrawTAIL);
     //   drawTail.setTranslateY(68);
     //   drawTail.setPrefWidth(120);
        drawTail.setAlignment(Pos.CENTER);
     //   drawTail.setTranslateX(xRows * size -280);


        Button drawHead = new Button("head");
        drawHead.setGraphic(new Circle(4, Color.BLUE));
        drawHead.setOnAction(this::handleDrawHED);
      //  drawHead.setTranslateY(68);
        drawHead.setAlignment(Pos.CENTER);
        // drawHead.setPrefWidth(120);
     //   drawHead.setTranslateX(xRows * size -270 );



        Button drawOR = new Button("Gate_OR");
        drawOR.setOnAction(this::handleDrawOR);
      //  drawOR.setTranslateY(108);
        drawOR.setAlignment(Pos.CENTER);
    //    drawOR.setPrefWidth(120);
     //   drawOR.setTranslateX(xRows * size -385 );


        Button drawXOR = new Button("Gare_XOR");
        drawXOR.setOnAction(this::handleDrawXOR);
     //   drawXOR.setTranslateY(108);
        drawXOR.setAlignment(Pos.CENTER);
    //   drawXOR.setPrefWidth(120);
     //   drawXOR.setTranslateX(xRows * size -375 );


        Button drawAND = new Button("Gate_AND");
        drawAND.setOnAction(this::handleDrawAND);
     //   drawAND.setTranslateY(108);
        drawAND.setAlignment(Pos.CENTER);
     //   drawAND.setPrefWidth(120);
     //   drawAND.setTranslateX(xRows * size -365 );


        Button drawDIODEL = new Button("Diode_LEFT");
        drawDIODEL.setOnAction(this::handleDrawDIODEL);
        drawDIODEL.setTranslateY(148);
        drawDIODEL.setAlignment(Pos.CENTER);
    //    drawDIODEL.setPrefWidth(140);
        drawDIODEL.setTranslateX(xRows * size-589);


        Button drawDIODELV = new Button("Diode_LEFT_V");
        drawDIODELV.setOnAction(this::handleDrawDIODELV);
     //   drawDIODELV.setTranslateY(148);
        drawDIODELV.setAlignment(Pos.CENTER);
    //    drawDIODELV.setPrefWidth(140);
     //   drawDIODELV.setTranslateX(xRows * size -664 );


        Button drawDIODER = new Button("Diode_RIGHT");
        drawDIODER.setOnAction(this::handleDrawDIODER);
     //   drawDIODER.setTranslateY(188);
        drawDIODER.setAlignment(Pos.CENTER);
     //   drawDIODER.setPrefWidth(140);
     //   drawDIODER.setTranslateX(xRows * size -668 );


        Button drawDIODERV = new Button("Diode_RIGHT_V");
        drawDIODERV.setOnAction(this::handleDrawDIODERV);
     //   drawDIODERV.setTranslateY(188);
        drawDIODERV.setAlignment(Pos.CENTER);
     //   drawDIODERV.setPrefWidth(160);
     //   drawDIODERV.setTranslateX(xRows * size -748  );


        Label label = new Label("Liczba generacji: ");
     //   label.setTranslateY(238);
        label.setAlignment(Pos.BASELINE_LEFT);
      //  label.setTranslateX(xRows * size  -945);
    //    label.setPrefWidth(120);


        genField = new TextField("20");
        genField.setTranslateY(238);
        genField.setTranslateX(xRows * size -935 );
        genField.setPrefWidth(80);


        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        start.setTranslateY(278);
        start.setAlignment(Pos.CENTER);
        start.setTranslateX(xRows * size -1123);
     //   start.setPrefWidth(120);


        Button start2 = new Button("Start_To_Infinity");
        start2.setOnAction(this::handleStart2);
        start2.setTranslateY(278);
        start2.setAlignment(Pos.CENTER);
        start2.setTranslateX(xRows * size -1108);
      //  start2.setPrefWidth(120);


        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        stop.setTranslateY(318);
        stop.setAlignment(Pos.CENTER);
        stop.setTranslateX(xRows * size -1270);
    //    stop.setPrefWidth(120);



        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        reset.setTranslateY(318);
        reset.setAlignment(Pos.CENTER);
        reset.setTranslateX(xRows * size -1260 );
    //    reset.setPrefWidth(120);


        Button clean = new Button("clean");
        clean.setOnAction(this::handleClean);
        clean.setTranslateY(318);
        clean.setAlignment(Pos.CENTER);
        clean.setTranslateX(xRows * size -1250);



        getChildren().addAll( label1, drawEmpty, drawConductor, drawTail, drawHead, drawOR, drawXOR, drawAND, drawDIODEL, drawDIODER, drawDIODELV, drawDIODERV, label, genField, start, start2, stop, reset, clean);

        Scene scene = new Scene(vb);
        primaryStage.setScene(scene);
        primaryStage.show();

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
                this.gridView.drawClean();
            }
        }
    }
}


