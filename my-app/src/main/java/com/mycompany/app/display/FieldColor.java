package com.mycompany.app.display;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum FieldColor{
    B(Color.BLACK),
    W(Color.WHITE),
    E(Color.TRANSPARENT);

    private Paint color;

    FieldColor(Color color){
        this.color = color;
    }

    public Paint getColor() {
        return color;
    }

    public char toChar() {
        if (FieldColor.B.equals(this)){
            return 'B';
        }
        if (FieldColor.W.equals(this)){
            return 'W';
        }
        if (FieldColor.E.equals(this)){
            return 'E';
        }
        return '\0';
    }
}