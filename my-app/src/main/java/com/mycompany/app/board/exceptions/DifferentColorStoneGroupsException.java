package com.mycompany.app.board.exceptions;

public class DifferentColorStoneGroupsException extends Exception{
    @Override
    public String getMessage() {
        return "Different stone color";
    }
}
