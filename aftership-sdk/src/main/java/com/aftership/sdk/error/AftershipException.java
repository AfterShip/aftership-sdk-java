package com.aftership.sdk.error;

public class AftershipException extends RuntimeException {
    public AftershipException(String message){
        super(message);
    }
    public AftershipException(String message, Throwable cause){
        super(message, cause);
    }
}
