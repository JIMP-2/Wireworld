package com.wireworld.model;

public class StandardRule implements SimulationRule {

    @Override
    public CellState getNextState(int x, int y, Board board) {
        int aliveNeighbours = countAliveNeighbours(x, y, board);

        if (board.getState(x, y) == CellState.EMPTY) {
            return CellState.EMPTY;
        }
        if (board.getState(x, y) == CellState.HEAD) {
            return CellState.TAIL;
        }
        if (board.getState(x, y) == CellState.TAIL) {
            return CellState.CONDUCTOR;
        }
        if (board.getState(x, y) == CellState.CONDUCTOR) {
            if (aliveNeighbours == 1 || aliveNeighbours == 2) {
            return CellState.HEAD;
        }
            else {
                return CellState.CONDUCTOR;
            }
            }
        return CellState.CONDUCTOR;
        }

    private int countAliveNeighbours(int x, int y, Board board) {
        int count = 0;

        count += countCell(x - 1, y - 1, board);
        count += countCell(x, y - 1, board);
        count += countCell(x + 1, y - 1, board);

        count += countCell(x - 1, y, board);
        count += countCell(x + 1, y, board);

        count += countCell(x - 1, y + 1, board);
        count += countCell(x, y + 1, board);
        count += countCell(x + 1, y + 1, board);

        return count;
    }

    private int countCell(int x, int y, Board board) {
        if (board.getState(x, y) == CellState.HEAD) {
            return 1;
        } else {
            return 0;
        }
    }
}
