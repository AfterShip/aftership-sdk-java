package com.aftership.sdk.rest;

import com.aftership.sdk.error.AftershipError;
import com.aftership.sdk.model.AftershipResponse;
import lombok.Getter;

@Getter
public class ResponseEntity<T> implements DataEntity<T> {
    private final AftershipResponse<T> response;
    private final AftershipError error;

    private ResponseEntity(AftershipResponse<T> response, AftershipError error) {
        this.response = response;
        this.error = error;
    }

    @Override
    public AftershipError getError() {
        return this.error;
    }

    @Override
    public T getData() {
        if (this.response == null) {
            return null;
        }
        return this.response.getData();
    }

    public static <T> ResponseEntity<T> makeError(AftershipError error) {
        return new ResponseEntity<>(null, error);
    }

    static <T> ResponseEntity<T> makeResponse(AftershipResponse<T> response) {
        return new ResponseEntity<>(response, null);
    }


}
