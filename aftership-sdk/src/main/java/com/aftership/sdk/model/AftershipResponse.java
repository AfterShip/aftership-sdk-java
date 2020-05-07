package com.aftership.sdk.model;

import lombok.Data;


@Data
public class AftershipResponse<T> {
    private Meta meta;
    private T data;

    public AftershipResponse(){
    }

    public AftershipResponse(T data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }
}
