package com.aftership.sdk.error;

public class AftershipException extends RuntimeException {

    public AftershipException(String message) {
        super(message);
    }

    public AftershipException(String message, Throwable cause) {
        super(message, cause);
    }

    public AftershipException(String message, Object data) {
        this(String.format("%s data:%s", message, data.toString()));
    }

    public AftershipException(String message, Object data, Throwable cause) {
        super(String.format("%s data:%s", message, data.toString()), cause);
    }

}
