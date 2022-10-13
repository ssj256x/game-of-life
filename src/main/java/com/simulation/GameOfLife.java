package com.simulation;

import java.awt.*;
import java.util.Random;

public class GameOfLife implements Simulation {

    private int[][] mainGrid;
    private final int[][] auxGrid;
    private final Random random;
    private boolean isAuxGrid;
    private final int rows;
    private final int cols;

    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.mainGrid = new int[rows][cols];
        this.auxGrid = new int[rows][cols];
        this.random = new Random();
        this.isAuxGrid = true;
        randomInit();
    }

    private void init() {
        int[][] genesis = Patterns.ACORN;
        int m = genesis.length;
        int n = genesis[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int x = mainGrid.length / 2;
                int y = mainGrid[0].length / 2;
                mainGrid[i + x][y + j] = genesis[i][j];
            }
        }
    }

    private void randomInit() {
        int m = mainGrid.length;
        int n = mainGrid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int x = mainGrid.length / 2;
                int y = mainGrid[0].length / 2;
                mainGrid[i][j] = random();
            }
        }
    }

    private int random() {
        return this.random.nextDouble() > 0.95 ? 1 : 0;
    }

    @Override
    public int[][] getGrid() {
        return this.mainGrid;
    }

    @Override
    public void updateState() {
        updateGrid(this.mainGrid, new int[this.mainGrid.length][this.mainGrid[0].length]);
    }

    @Override
    public void draw(Graphics g, GridDetails gridDetails) {
        int maxScreenCols = gridDetails.getScreenCols();
        int maxScreenRows = gridDetails.getScreenRows();
        int tileSize = gridDetails.getTileSize();

        for (int i = 0; i < maxScreenRows; i++) {
            for (int j = 0; j < maxScreenCols; j++) {
                int r = i * tileSize;
                int c = j * tileSize;

                g.setColor(Color.WHITE);

                if (mainGrid[i][j] == 1)
                    g.fillRect(r, c, tileSize, tileSize);
            }
        }
    }

    private void updateGrid(int[][] src, int[][] dest) {
        for (int i = 0; i < dest.length; i++) {
            for (int j = 0; j < dest[0].length; j++) {
                int neighbours = countNeighbours(src, i, j);
                int state = src[i][j];

                if (state == 0 && neighbours == 3) {
                    dest[i][j] = 1;
                } else if (state == 1 && (neighbours < 2 || neighbours > 3)) {
                    dest[i][j] = 0;
                } else {
                    dest[i][j] = state;
                }
            }
        }
        this.mainGrid = dest;
    }

    private int countNeighbours(int[][] grid, int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r = (i + x + this.rows) % this.rows;
                int c = (j + y + this.rows) % this.cols;
                if (isValid(grid, r, c)) count += grid[r][c];
            }
        }
        return count - grid[x][y];
    }

    private boolean isValid(int[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
}
