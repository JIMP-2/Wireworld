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
import com.wireworld.model.Board;
import com.wireworld.model.BasicBoard;
import com.wireworld.model.CellState;
import com.wireworld.model.StandardRule;



    public class GridView extends VBox {

        public static final int EDITING = 0;
        public static final int SIMULATING = 1;

        public static int xRows;
        public static int yColumns;
        public int size;
        public int c;

        private Canvas canvas;

        private Affine affine;

        private Simulation simulation;
        private Board initialBoard;

        private CellState drawMode = CellState.EMPTY;

        private int applicationState = EDITING;

        public GridView(int xRows, int yColumns, int size) {

            this.xRows=xRows;
            this.yColumns=yColumns;
            this.size=size;
            this.c =c;

            this.canvas = new Canvas(xRows*size, yColumns*size);
            this.canvas.setOnMousePressed(this::handleDraw);
            this.canvas.setOnMouseDragged(this::handleDraw);
            this.canvas.setOnMouseMoved(this::handleMoved);



            Toolbar toolbar = new Toolbar(this);



            Pane spacer = new Pane();
            spacer.setMinSize(0, 0);
            spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            VBox.setVgrow(spacer, Priority.ALWAYS);


            this.getChildren().addAll(toolbar, this.canvas, spacer);

            this.affine = new Affine();
            this.affine.appendScale(size,size);

            this.initialBoard = new BasicBoard(xRows, yColumns);
        }



        private void handleMoved(MouseEvent mouseEvent) {
            Point2D simCoord = this.getSimulationCoordinates(mouseEvent);

        }



        private void handleDraw(MouseEvent event) {

            if (this.applicationState == SIMULATING) {
                return;
            }

            Point2D simCoord = this.getSimulationCoordinates(event);

            int simX = (int) simCoord.getX();
            int simY = (int) simCoord.getY();



            this.initialBoard.setState(simX, simY, drawMode);
            draw();
        }

        private Point2D getSimulationCoordinates(MouseEvent event) {
            double mouseX = event.getX();
            double mouseY = event.getY();

            try {
                Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);
                return simCoord;
            } catch (NonInvertibleTransformException e) {
                throw new RuntimeException("Non invertible transform");
            }
        }

        public void draw() {
            GraphicsContext g = this.canvas.getGraphicsContext2D();
            g.setTransform(this.affine);

            g.setFill(Color.LIGHTGRAY);
            g.fillRect(0, 0, xRows*size, yColumns*size);

            if (this.applicationState == EDITING) {
                drawSimulation(this.initialBoard);
            } else {
                drawSimulation(this.simulation.getBoard());
            }

            g.setStroke(Color.GRAY);
            g.setLineWidth(0.05);
            for (int x = 0; x <= this.initialBoard.getWidth(); x++) {
                g.strokeLine(x, 0, x, yColumns);
            }

            for (int y = 0; y <= this.initialBoard.getHeight(); y++) {
                g.strokeLine(0, y, xRows, y);
            }

        }


        private void drawSimulation(Board simulationToDraw) {
            GraphicsContext g = this.canvas.getGraphicsContext2D();
            g.setTransform(this.affine);
            g.setFill(Color.BLACK);
             g.fillRect(0, 0, xRows*size, yColumns*size);
            g.setFill(Color.WHITE);
            for (int x = 0; x < simulationToDraw.getWidth(); x++) {
                for (int y = 0; y < simulationToDraw.getHeight(); y++) {
                    if (simulationToDraw.getState(x, y) == CellState.EMPTY) {
                        g.setFill(Color.BLACK);
                        g.fillRect(x, y, 1, 1);
                    }
                    if (simulationToDraw.getState(x, y) == CellState.CONDUCTOR) {
                        g.setFill(Color.YELLOW);
                        g.fillRect(x, y, 1, 1);
                    }
                    if (simulationToDraw.getState(x, y) == CellState.HEAD) {
                        g.setFill(Color.BLUE);
                        g.fillRect(x, y, 1, 1);
                    }
                    if (simulationToDraw.getState(x, y) == CellState.TAIL) {
                        g.setFill(Color.RED);
                        g.fillRect(x, y, 1, 1);
                    }
                }
            }
        }
        public Simulation getSimulation() {
            return this.simulation;
        }

        public void setDrawMode(CellState newDrawMode) {
            this.drawMode = newDrawMode;

        }

        public void setApplicationState(int applicationState) {
            if (applicationState == this.applicationState) {
                return;
            }

            if (applicationState == SIMULATING) {
                this.simulation = new Simulation(this.initialBoard, new StandardRule());
            }

            this.applicationState = applicationState;

            System.out.println("Application State: " + this.applicationState);
        }

        public int getApplicationState() {
            return applicationState;
        }

        public void clean(int x, int y) {
            this.initialBoard.setState(x,y,CellState.EMPTY);
            draw();
        }

    }
