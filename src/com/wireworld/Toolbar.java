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


        this.getItems().addAll(save);

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


}