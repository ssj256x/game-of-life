package com.simulation;

import java.awt.*;

public class LangtonsAnt implements Simulation {

    private final int[][] mainGrid;
    private final int rows;
    private final int cols;

    private final static int UP = 0;
    private final static int RIGHT = 1;
    private final static int DOWN = 2;
    private final static int LEFT = 3;

    private int dir;
    private int[] ant;

    public LangtonsAnt(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.mainGrid = new int[rows][cols];
        this.dir = UP;
        this.ant = new int[2];
        ant[0] = this.rows / 2;
        ant[1] = this.cols / 2;
    }


    @Override
    public int[][] getGrid() {
        return this.mainGrid;
    }

    @Override
    public void updateState() {
        int speedUp = 1;
        while (speedUp-- > 0) {
            int x = this.ant[0];
            int y = this.ant[1];
            int state = this.mainGrid[x][y];

            if (state == 0) {
                turnRight();
                this.mainGrid[x][y] = 1;
            } else {
                turnLeft();
                this.mainGrid[x][y] = 0;
            }
            updateDirection();
        }
    }

    @Override
    public void draw(Graphics g, GridDetails gridDetails) {
        int maxScreenRows = gridDetails.getScreenRows();
        int maxScreenCols = gridDetails.getScreenCols();
        int tileSize = gridDetails.getTileSize();

        for (int i = 0; i < maxScreenRows; i++) {
            for (int j = 0; j < maxScreenCols; j++) {
                int r = i * tileSize;
                int c = j * tileSize;

                g.setColor(Color.WHITE);

                if (mainGrid[i][j] == 1)
                    g.fillRect(r, c, tileSize, tileSize);

                if(i == ant[0] && j == ant[1]) {
                    g.setColor(Color.RED);
                    g.fillRect(r, c, tileSize, tileSize);
                }
            }
        }
    }

    private void updateDirection() {
        if (dir == UP) ant[1]--;
        else if (dir == RIGHT) ant[0]++;
        else if (dir == DOWN) ant[1]++;
        else if (dir == LEFT) ant[0]--;

        ant[0] = (ant[0] + rows) % rows;
        ant[1] = (ant[1] + cols) % cols;
    }

    private void turnLeft() {
        dir++;
        if (dir > LEFT) dir = UP;
    }

    private void turnRight() {
        dir--;
        if (dir < UP) dir = LEFT;
    }
}
