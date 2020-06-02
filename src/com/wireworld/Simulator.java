package com.wireworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private GridView gridView;
    private Simulation simulation;
    private int generations;

    public Simulator(GridView gridView, Simulation simulation, int generations) {
        this.gridView = gridView;
        this.simulation = simulation;
        this.generations = generations;

        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));

    }

    public void timelineNumber(int generations) {

        this.timeline.setCycleCount(generations);

    }

    public void timelineInf() {
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