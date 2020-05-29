package com.wireworld;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import javafx.stage.Stage;


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

        private Stage primaryStage;

        public GridView(BasicBoard initialBoard, int size, Stage primaryStage) {
            this.primaryStage = primaryStage;

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());

            this.xRows=initialBoard.getWidth();
            this.yColumns=initialBoard.getHeight();
            this.size=size;
            this.c =c;

            this.canvas = new Canvas(xRows*size, yColumns*size);
            this.canvas.setOnMousePressed(this::handleDraw);
            this.canvas.setOnMouseDragged(this::handleDraw);
            this.canvas.setOnMouseMoved(this::handleMoved);

            Toolbar toolbar = new Toolbar(this, primaryStage, size);

            Pane spacer = new Pane();
            spacer.setMinSize(0, 0);
            spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            VBox.setVgrow(spacer, Priority.ALWAYS);

            this.getChildren().addAll(toolbar, this.canvas, spacer);

            this.affine = new Affine();
            this.affine.appendScale(size,size);

            this.initialBoard = initialBoard;
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
                    if (simulationToDraw.getState(x, y) == CellState.DIODE_RIGHT) {

                        g.setFill(Color.YELLOW);
                        g.fillRect(x, y, 1, 1);
                        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 1, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 2, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 3, y - 1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 3, y + 1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 4, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 4, y - 1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 4, y + 1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 5, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 6, y, CellState.CONDUCTOR);

                        setDrawMode(CellState.CONDUCTOR);
                    }

                    if (simulationToDraw.getState(x, y) == CellState.DIODE_RIGHT_VERTICAL) {

                        g.setFill(Color.YELLOW);
                        g.fillRect(x, y, 1, 1);
                        g.fillRect(x-1, y+3, 1, 1);
                        g.fillRect(x-1, y+4, 1, 1);
                        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y+1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x  -1, y +3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 1, y + 3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x -1, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y +4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 1, y + 4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y+5, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y+6, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y+7, CellState.CONDUCTOR);

                        setDrawMode(CellState.CONDUCTOR);
                    }

                    if (simulationToDraw.getState(x, y) == CellState.DIODE_LEFT) {

                        g.setFill(Color.YELLOW);
                        g.fillRect(x, y, 1, 1);
                        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 1, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 2, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 4, y - 1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 4, y + 1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 3, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 3, y - 1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 3, y + 1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 5, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 6, y, CellState.CONDUCTOR);

                        setDrawMode(CellState.CONDUCTOR);


                    }


                    if (simulationToDraw.getState(x, y) == CellState.DIODE_LEFT_VERTICAL) {

                        g.setFill(Color.YELLOW);
                        g.fillRect(x, y, 1, 1);
                        g.fillRect(x-1, y+3, 1, 1);
                        g.fillRect(x-1, y+4, 1, 1);
                        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y+1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x  -1, y +4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 1, y + 4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x -1, y+3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y +3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x + 1, y + 3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y+5, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y+6, CellState.CONDUCTOR);
                        simulationToDraw.setState(x , y+7, CellState.CONDUCTOR);

                        setDrawMode(CellState.CONDUCTOR);
                    }

                    if (simulationToDraw.getState(x, y) == CellState.GATE_AND) {
                        g.setFill(Color.YELLOW);
                        g.fillRect(x, y, 1, 1);
                        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+1, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+3, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+4, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+4, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+6, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+4, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+3, y+5, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y+6, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+1, y+6, CellState.CONDUCTOR);
                        simulationToDraw.setState(x, y+6, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+6, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+7, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+8, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+9, y+4, CellState.CONDUCTOR);

                        setDrawMode(CellState.CONDUCTOR);


                    }

                    if (simulationToDraw.getState(x, y) == CellState.GATE_OR) {
                        g.setFill(Color.YELLOW);
                        g.fillRect(x, y, 1, 1);
                        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+1, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+3, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+4, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+4, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+6, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+7, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+8, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+4, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+3, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+1, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x, y+4, CellState.CONDUCTOR);


                        setDrawMode(CellState.CONDUCTOR);

                    }
                    if (simulationToDraw.getState(x, y) == CellState.GATE_XOR) {
                        g.setFill(Color.YELLOW);
                        g.fillRect(x, y, 1, 1);
                        simulationToDraw.setState(x, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+1, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+3, y+1, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+3, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+4, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+2, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y+3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+6, y+3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+7, y+3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+8, y+3, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+3, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+4, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+5, y+4, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+3, y+5, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+2, y+6, CellState.CONDUCTOR);
                        simulationToDraw.setState(x+1, y+6, CellState.CONDUCTOR);
                        simulationToDraw.setState(x, y+6, CellState.CONDUCTOR);


                        setDrawMode(CellState.CONDUCTOR);

                    }
                }
            }
        }
        public Simulation getSimulation() {
            return this.simulation;
        }

        public void setSimulation(Simulation simulation) {
            this.simulation = simulation;
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

        public Board getInitialBoard() {
            return this.initialBoard;
        }

    }
