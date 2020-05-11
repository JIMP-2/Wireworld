package com.wireworld;

public class Simulation {
    int width;
    int height;
    State[][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;

        this.board = new State[width][height];
    }

    public void setEmpty(int x, int y) {
        if(x<0 || x>=width) {
            return;
        }
        if(y<0 || y>=height) {
            return;
        }
        this.board[x][y] = State.EMPTY;
    }

    public void setHead(int x, int y) {
        if(x<0 || x>=width) {
            return;
        }
        if(y<0 || y>=height) {
            return;
        }
        this.board[x][y] = State.HEAD;
    }

    public void setTail(int x, int y) {
        if(x<0 || x>=width) {
            return;
        }
        if(y<0 || y>=height) {
            return;
        }
        this.board[x][y] = State.TAIL;
    }

    public void setConductor(int x, int y) {
        if(x<0 || x>=width) {
            return;
        }
        if(y<0 || y>=height) {
            return;
        }
        this.board[x][y] = State.CONDUCTOR;
    }

    public State getState(int x, int y) {
        if(x<0 || x>=width) {
            return State.EMPTY;
        }
        if(y<0 || y>=height) {
            return State.EMPTY;
        }
        return board[x][y];
    }
}
