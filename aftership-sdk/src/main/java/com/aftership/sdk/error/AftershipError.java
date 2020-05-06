package com.aftership.sdk.error;

import com.aftership.sdk.lib.StrUtil;
import com.aftership.sdk.model.AftershipResponse;
import com.aftership.sdk.model.Meta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AftershipError {
    private String type = StrUtil.EMPTY;
    private String message = StrUtil.EMPTY;
    private String code = StrUtil.EMPTY;
    private Object data = null;

    public AftershipError() {
    }

    public static AftershipError makeSdkError(ErrorType errorType, String message) {
        return makeSdkError(errorType, message, null);
    }

    public static AftershipError makeSdkError(ErrorType errorType, String message, Object data) {
        AftershipError error = new AftershipError();
        error.setType(errorType.getName());
        error.setMessage(message);
        error.setData(data);
        return error;
    }

    public static AftershipError makeRequestError(ErrorType errorType, Throwable t, Object data) {
        return makeRequestError(errorType, t.getMessage(), data);
    }

    public static AftershipError makeRequestError(ErrorType errorType, String message, Object data) {
        AftershipError error = new AftershipError();
        error.setType(errorType.getName());
        error.setMessage(message);
        error.setData(data);
        return error;
    }

    public static AftershipError getApiError(AftershipResponse responseBody) {
        if (responseBody == null) {
            AftershipError error = new AftershipError();
            error.setType(ErrorType.InternalError.getName());
            error.setCode("500");
            return error;
        }


        AftershipError error = new AftershipError();

        Meta meta = responseBody.getMeta();
        if (meta != null) {
            error.setType(meta.getType());
            error.setCode(meta.getCode().toString());
            error.setMessage(meta.getMessage());
        }

        Object data = responseBody.getData();
        if (data != null) {
            error.setData(data);
        }

        return error;
    }

}
