package com.mycompany.app.client.exceptions;

public class FromServerException extends Exception {
    public FromServerException() {
        //EmptyOnPurpose
    }

    public FromServerException(byte[] msg) {
        super(new String(msg));
    }
}
