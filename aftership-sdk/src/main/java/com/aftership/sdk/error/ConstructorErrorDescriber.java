package com.aftership.sdk.error;

public enum ConstructorErrorDescriber {
    ConstructorInvalidApiKey("ConstructorInvalidApiKey", "ConstructorError: Invalid API key");

    private String name;
    private String message;

    ConstructorErrorDescriber(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return this.name;
    }

    public String getMessage(){
        return this.message;
    }

}
