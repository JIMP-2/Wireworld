package com.wireworld;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class WelcomeView extends GridPane {
    Stage primaryStage;

    public WelcomeView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.setPadding(new Insets(20, 100, 20, 100));
        this.setVgap(30);
        this.setHgap(20);
        this.setAlignment(Pos.CENTER);
    }

    public void draw() {
        Text welcome = new Text("W I R E W O R L D");
        welcome.setStyle("   -fx-font: 40px Tahoma;\n" +
                "    -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, blue 0%, red 33%, yellow 66%);\n" +
                "    -fx-stroke: black;\n" +
                "    -fx-stroke-width: 1;\n" +
                "   -fx-alignment: center;");
        HBox welcomeBox= new HBox();
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.getChildren().addAll(welcome);
        this.add(welcomeBox, 0, 0);

        TextField height = new TextField("20");
        Label label = new Label ("Wysokosc: ");
        int ymax = Integer.parseInt(height.getText());
        HBox box = new HBox();
        height.setMaxWidth(70);
        label.setStyle("-fx-font-size: 15px;" );
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(label, height);

        TextField width = new TextField("20");
        Label widthLabel = new Label("Szerokosc: ");
        widthLabel.setStyle("-fx-font-size: 15px;" );
        width.setMaxWidth(70);
        //StackPane textContainer = new StackPane(width);
        HBox widthBox = new HBox();
        widthBox.setAlignment(Pos.CENTER);
        widthBox.getChildren().addAll(widthLabel, width);

        Button create = new Button("Create");
        create.setMaxWidth(Double.MAX_VALUE);
        create.setMaxWidth(120);
        create.setStyle("-fx-text-alignment: center;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;");
        // grid.add(create, 2, 2);
        HBox data = new HBox();
        data.setAlignment(Pos.CENTER);
        data.getChildren().addAll(box, widthBox, create);
        data.setSpacing(30);
        this.add(data, 0, 2);
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                int y = Integer.parseInt(height.getText());
                int x = Integer.parseInt(width.getText());
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
               // System.out.println(primScreenBounds.getWidth() +" "+ primScreenBounds.getHeight());
               // System.out.println(x+" "+y);
                int sizeX = (int)(primScreenBounds.getWidth()-200-30-10-x)/x; // to potem trzeba będzie poprawić
                int sizeY = (int)((primScreenBounds.getHeight()-30-10-y)/y); // (jak bedziemy wiedzialy, ile nam panel z przyciskami zajmuje
                int size = sizeX<sizeY ? sizeX : sizeY;

                //System.out.println(size);

                GridView gridView = new GridView(x, y, size);
                Scene scene = new Scene(gridView);
                primaryStage.setScene(scene);
                primaryStage.show();

                gridView.draw();

                primaryStage.setScene(scene);
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });

        Text instruction = new Text("Stwórz nową planszę o wybranym rozmiarze lub wczytaj z pliku");
        instruction.setStyle("-fx-font: 20px Tahoma;");
        HBox insBox = new HBox();
        insBox.setAlignment(Pos.CENTER);
        insBox.getChildren().addAll(instruction);
        this.add(insBox, 0, 1);

        Button file = new Button("Wczytaj planszę\nz pliku ");
        file.setStyle("-fx-text-alignment: center;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;");
        GridPane.setHalignment(file, HPos.CENTER);
        this.add(file, 0, 3);

        file.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("SER Files", "*.ser"));
                Stage stage = new Stage();
                File selectedFile = fileChooser.showOpenDialog(stage);
            }
        });
    }
}
