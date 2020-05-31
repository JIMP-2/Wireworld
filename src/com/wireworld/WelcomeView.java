package com.wireworld;

import com.wireworld.model.BasicBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.*;

import static com.wireworld.Alerts.showAlert;
import static com.wireworld.Alerts.showAlertInteger;

import java.io.*;


public class WelcomeView extends GridPane {
    Stage primaryStage;

    public WelcomeView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.setPadding(new Insets(20, 100, 20, 100));
        this.setVgap(30);
        this.setHgap(20);
        this.setAlignment(Pos.CENTER);
    }



        public  void draw() {
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


                int x = -1;
                int y = -1;


                try {
                y = Integer.parseInt(height.getText());
                x = Integer.parseInt(width.getText());
                } catch (NumberFormatException n) {
                    showAlertInteger();
                }

                if (x >= 0 || y >= 0)
                try {

                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    // System.out.println(primScreenBounds.getWidth() +" "+ primScreenBounds.getHeight());
                    // System.out.println(x+" "+y);
                    int sizeX = (int) (primScreenBounds.getWidth()-310) / x; // to potem trzeba będzie poprawić
                    int sizeY = (int) ((primScreenBounds.getHeight()-75) / y); // (jak bedziemy wiedzialy, ile nam panel z przyciskami zajmuje
                    int size = sizeX < sizeY ? sizeX : sizeY;

                    BasicBoard initialBoard = new BasicBoard(x, y);


                    GridView gridView = new GridView(initialBoard, size, primaryStage);
                    Scene scene = new Scene(gridView);
                    primaryStage.setScene(scene);
                    primaryStage.show();

                    gridView.draw();

                    primaryStage.setScene(scene);
                    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                }  catch (Exception e) {
                    showAlert();
                }
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
                fileChooser.setInitialDirectory(new File("data"));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SER Files", "*.ser"));
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if(selectedFile!=null) {
                    try {
                        BasicBoard st = null;

                        FileInputStream fi = new FileInputStream(selectedFile);
                        ObjectInputStream oi = new ObjectInputStream(fi);

                        Object obj = oi.readObject();

                        oi.close();
                        fi.close();
                        st = (BasicBoard) obj;
                        System.out.println(st.toString());

                        int y = st.getHeight();
                        int x = st.getWidth();
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        int sizeX = (int) (primScreenBounds.getWidth() - 310) / x; // to potem trzeba będzie poprawić
                        int sizeY = (int) ((primScreenBounds.getHeight() - 75) / y); // (jak bedziemy wiedzialy, ile nam panel z przyciskami zajmuje
                        int size = sizeX < sizeY ? sizeX : sizeY;

                        GridView gridView = new GridView(st, size, primaryStage);
                        Scene scene = new Scene(gridView);
                        primaryStage.setScene(scene);
                        primaryStage.show();

                        gridView.draw();

                        primaryStage.setScene(scene);
                        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);


                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    } catch (IOException e) {
                        System.out.println("Error initializing stream");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();

                    }
                }


            System.out.println("click");






            }
        });
    }

}
