package com.mycompany.app.board.exceptions;

public class OutOfBorderException extends Exception{
    @Override
    public String getMessage() {
        return "Stone placement out of border";
    }
}