package com.mycompany.app.board.exceptions;

public class IncorrectStonePlacementException extends Exception{
    @Override
    public String getMessage() {
        return "Incorrect stone exception";
    }
}
