package com.mycompany.app.board;


public enum StoneColor {
    BLACK,
    WHITE;

    public static StoneColor valueOf(int ind){
        return StoneColor.values()[ind];
    }
}
