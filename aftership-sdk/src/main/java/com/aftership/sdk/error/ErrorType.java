package com.aftership.sdk.error;

public enum ErrorType {
    ConstructorError("ConstructorError"),
    HandlerError("HandlerError"),
    InternalError("InternalError");

    private String name;

    ErrorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
