package com.wireworld;

import com.wireworld.model.CellState;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class Toolbar extends ToolBar {

    private GridView gridView;
    private Stage primaryStage;

    private Label cursor;
    private String cursorFormat = "Cursor: (%d, %d)";



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

        if (selectedFile != null) {
            if (!selectedFile.getName().contains(".ser")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".ser");
            }
            try {
                FileOutputStream fo = new FileOutputStream(selectedFile);
                ObjectOutputStream oo = new ObjectOutputStream(fo);

                oo.writeObject(this.gridView.getInitialBoard());

                oo.close();
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}