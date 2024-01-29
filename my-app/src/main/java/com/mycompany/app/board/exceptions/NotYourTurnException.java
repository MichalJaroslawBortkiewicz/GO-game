package com.mycompany.app.board.exceptions;

public class NotYourTurnException extends Exception {
    @Override
    public String getMessage() {
        return "Not your turn";
    }
}
