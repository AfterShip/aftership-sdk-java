package com.aftership.sdk.rest;

import com.aftership.sdk.error.AftershipError;

public interface DataEntity<T> {
    T getData();

    AftershipError getError();

    default boolean hasError() {
        return getError() != null;
    }

}
