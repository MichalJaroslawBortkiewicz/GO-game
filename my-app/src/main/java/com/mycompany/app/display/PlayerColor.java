package com.mycompany.app.display;

import javafx.scene.paint.Color;


public enum PlayerColor {
    BLACK(Color.BLACK),
    WHITE(Color.WHITE);

    private final Color color;

    PlayerColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }
}