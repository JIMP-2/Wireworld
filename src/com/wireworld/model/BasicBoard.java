package com.wireworld.model;

import java.io.Serializable;

public class BasicBoard implements Board, Serializable {

    private int width;
    private int height;
    private CellState[][] board;

    public BasicBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new CellState[width][height];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                setState(x, y, CellState.EMPTY);
            }
        }

    }

    public String toString() {
        return "wczytano, hurra";
    }

    public BasicBoard(int width, int height, CellState[][] board) {
        this.width = width;
        this.height = height;
        this.board = board;
    }


    @Override
    public BasicBoard copy() {
        BasicBoard copy = new BasicBoard(this.width, this.height);

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                copy.setState(x, y, this.getState(x, y));
            }
        }

        return copy;
    }

    @Override
    public CellState getState(int x, int y) {
        if (x < 0 || x >= this.width) {
            return CellState.EMPTY;
        }

        if (y < 0 || y >= this.height) {
            return CellState.EMPTY;
        }

        return this.board[x][y];
    }

    @Override
    public void setState(int x, int y, CellState cellState) {
        if (x < 0 || x >= this.width) {
            return;
        }

        if (y < 0 || y >= this.height) {
            return;
        }

        this.board[x][y] = cellState;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
