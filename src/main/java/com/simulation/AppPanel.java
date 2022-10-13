package com.simulation;

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel implements Runnable {

    private final int tileSize;
    private final int maxScreenRows;
    private final int maxScreenCols;
    private final double drawInterval;
    private final Simulation simulation;
    private final GridDetails gridDetails;
    private transient Thread thread;
    private static final double ONE_SECOND_BILLION = 1_000_000_000;

    private AppPanel(Builder builder) {
        this.maxScreenCols = builder.maxScreenCols;
        this.maxScreenRows = builder.maxScreenRows;
        this.simulation = builder.simulation;

        this.tileSize = builder.originalTileSize * builder.scale;
        int screenWidth = this.tileSize * this.maxScreenRows;
        int screenHeight = this.tileSize * this.maxScreenCols;
        this.drawInterval = ONE_SECOND_BILLION / builder.fps;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.gridDetails = GridDetails.builder()
                .screenRows(maxScreenRows)
                .screenCols(maxScreenCols)
                .tileSize(tileSize)
                .build();
    }

    public void startMainThread() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(thread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta < 1) continue;

            if(delta >= 1) {
                this.update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        this.simulation.updateState();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.simulation.draw(g, gridDetails);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int originalTileSize;
        private int scale;
        private int maxScreenRows;
        private int maxScreenCols;
        private int fps;
        private Simulation simulation;

        public Builder originalTileSize(int originalTileSize) {
            this.originalTileSize = originalTileSize;
            return this;
        }

        public Builder scale(int scale) {
            this.scale = scale;
            return this;
        }

        public Builder maxScreenRows(int maxScreenRows) {
            this.maxScreenRows = maxScreenRows;
            return this;
        }

        public Builder maxScreenCols(int maxScreenCols) {
            this.maxScreenCols = maxScreenCols;
            return this;
        }

        public Builder fps(int fps) {
            this.fps = fps;
            return this;
        }

        public Builder simulation(Simulation simulation) {
            this.simulation = simulation;
            return this;
        }

        public AppPanel build() {
            return new AppPanel(this);
        }
    }
}
