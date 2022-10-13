package com.simulation;

import java.awt.*;

public interface Simulation {

    int[][] getGrid();

    void updateState();

    void draw(Graphics g, GridDetails gridDetails);
}
