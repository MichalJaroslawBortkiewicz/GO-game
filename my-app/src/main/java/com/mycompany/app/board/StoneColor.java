package com.mycompany.app.board;

import javafx.scene.paint.Color;


public enum StoneColor {
    BLACK(Color.BLACK),
    WHITE(Color.WHITE);

    private final Color color;

    StoneColor(Color color){
        this.color = color;
    }

    public static StoneColor valueOf(int ind){
        return StoneColor.values()[ind];
    }

    public Color getColor(){
        return color;
    }
}
