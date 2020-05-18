package com.wireworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;

public class Simulator {

    private Timeline timeline;
    private GridView gridView;
    private Simulation simulation;

    public Simulator(GridView gridView, Simulation simulation) {
        this.gridView = gridView;
        this.simulation = simulation;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));


        this.timeline.setCycleCount(Timeline.INDEFINITE);

    }


    private void doStep(ActionEvent actionEvent) {
        this.simulation.step();
        this.gridView.draw();
    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }
}