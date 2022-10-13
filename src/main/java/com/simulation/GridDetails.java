package com.simulation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GridDetails {
    private final int screenRows;
    private final int screenCols;
    private final int tileSize;
}
