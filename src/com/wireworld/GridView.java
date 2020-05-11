package com.wireworld;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;



public class GridView extends VBox {

    private Button conductorButton, emptyButton, headButton, tailButton;
    private int xRows, yColumns, size;
    private Canvas canvas;
    private Simulation simulation;
    private Affine affine;
    private State stateDrawMode = State.CONDUCTOR;

    public GridView(int xRows, int yColumns, int size) {

        this.conductorButton = new Button("conductor");
        this.emptyButton = new Button("empty");
        this.headButton = new Button("head");
        this.tailButton = new Button("tail");

        conductorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setStateDrawMode(State.CONDUCTOR);
            }
        });
        emptyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setStateDrawMode(State.EMPTY);
            }
        });
        headButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setStateDrawMode(State.HEAD);
            }
        });
        tailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setStateDrawMode(State.TAIL);
            }
        });

        HBox drawButtons = new HBox();
        drawButtons.getChildren().addAll(conductorButton, headButton, tailButton, emptyButton);
        drawButtons.setSpacing(10);

        this.xRows=xRows;
        this.yColumns=yColumns;
        this.size=size;

        this.canvas = new Canvas(xRows*size, yColumns*size);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        this.simulation = new Simulation(xRows, yColumns);
        this.affine = new Affine();
        this.affine.appendScale(size, size);


        this.getChildren().addAll(drawButtons, this.canvas);

    }

    public void setStateDrawMode(State state) {
        this.stateDrawMode = state;
    }

    private void handleDraw(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        //System.out.println(mouseX + ", " + mouseY);
        try {
            Point2D coord = this.affine.inverseTransform(mouseX, mouseY);
            int coordX = (int) coord.getX();
            int coordY = (int) coord.getY();

            if(this.stateDrawMode == State.EMPTY) {
                this.simulation.setEmpty(coordX, coordY);
                draw();
            }
            else if(this.stateDrawMode == State.CONDUCTOR) {
                this.simulation.setConductor(coordX, coordY);
                draw();
            }
            else if(this.stateDrawMode == State.HEAD) {
                this.simulation.setHead(coordX, coordY);
                draw();
            }
            else if(this.stateDrawMode == State.TAIL) {
                this.simulation.setTail(coordX, coordY);
                draw();
            }
            //System.out.println(stateDrawMode);

        }catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, xRows*size, yColumns*size);
        g.setFill(Color.WHITE);
        for(int x=0; x<this.simulation.width; x++) {
            for(int y=0; y<this.simulation.height; y++) {
                if(simulation.getState(x,y) == State.EMPTY) {
                    g.setFill(Color.BLACK);
                    g.fillRect(x, y, 1, 1);
                }
                if(simulation.getState(x,y) == State.CONDUCTOR) {
                    g.setFill(Color.YELLOW);
                    g.fillRect(x, y, 1, 1);
                }
                if(simulation.getState(x,y) == State.HEAD) {
                    g.setFill(Color.BLUE);
                    g.fillRect(x, y, 1, 1);
                }
                if(simulation.getState(x,y) == State.TAIL) {
                    g.setFill(Color.RED);
                    g.fillRect(x, y, 1, 1);
                }

            }
        }
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.02);
        for(int x=0; x<=this.simulation.width; x++) {
            g.strokeLine(x, 0, x, yColumns);
        }

        for(int y=0; y<=this.simulation.height; y++) {
            g.strokeLine(0, y, xRows, y );
        }

    }
}
