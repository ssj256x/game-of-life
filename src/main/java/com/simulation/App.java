package com.simulation;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Game of Life");

        int cols = 200;
        int rows = 200;

        AppPanel appPanel = AppPanel.builder()
                .originalTileSize(2)
                .scale(1)
                .maxScreenCols(cols)
                .maxScreenRows(rows)
                .fps(200)
//                .simulation(new GameOfLife(rows, cols))
                .simulation(new LangtonsAnt(rows, cols))
                .build();

        window.add(appPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        appPanel.startMainThread();
    }
}
