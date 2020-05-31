package com.wireworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import com.wireworld.model.Board;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.CellState;
import com.wireworld.model.StandardRule;
import javafx.event.ActionEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

import static com.wireworld.GridView.xRows;


public class Toolbar extends ToolBar {

    private GridView gridView;
    private int generations;
    private TextField genField;
    private Stage primaryStage;

    private Label cursor;
    private String cursorFormat = "Cursor: (%d, %d)";


    private Simulator simulator;
    private Simulation simulation;

    public Toolbar(GridView gridView, Stage primaryStage, int size) {
        this.primaryStage = primaryStage;
        this.gridView = gridView;
        this.gridView.setDrawMode(CellState.CONDUCTOR);

        Button back = new Button("Go back");
        back.setOnAction(this::handleBack);


        Button save = new Button("Save file");
        save.setOnAction(this::handleSave);
        save.setAlignment(Pos.CENTER);

        this.cursor = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getItems().addAll(back, save, spacer, cursor);

    }

    public void setCursorCoord(int x, int y) {
        this.cursor.setText(String.format(cursorFormat, x, y));
    }

    private void handleBack(ActionEvent actionEvent) {
        WelcomeView welcomeView = new WelcomeView(primaryStage);
        welcomeView.draw();
        Scene scene = new Scene(welcomeView);
        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
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