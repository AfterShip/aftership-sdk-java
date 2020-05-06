package com.aftership.sdk.rest;

import com.aftership.sdk.error.AftershipError;
import com.aftership.sdk.lib.Define;

public interface DataEntity<T> {
    T getData();

    AftershipError getError();

    default boolean hasError() {
        return getError() != null && getError().getCode() != Define.ApiSuccessfulCode;
    }

}
