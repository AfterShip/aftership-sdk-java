package com.aftership.sdk.error;

public class AftershipException extends RuntimeException {
    private String message;

    public AftershipException(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
