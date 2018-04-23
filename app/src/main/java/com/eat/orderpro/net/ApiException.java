package com.eat.orderpro.net;

import com.eat.eatarms.utils.Constant;

/**
 * @author leo
 */
public class ApiException extends RuntimeException {
    enum ExceptionReason {
        /**
         * 连接失败
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 无效连接
         */
        BAD_NETWORK,
        /**
         * 解析出错
         */
        PARSE_ERROR,
        /**
         * 其他未知错误
         */
        UNKNOWN_ERROR
    }

    private int mErrorCode;

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    public boolean isInvalidToken() {
        return mErrorCode == Constant.RESPONSE_CODE_INVALID_TOKEN;
    }

    public boolean isNeedUpdate() {
        return mErrorCode == Constant.RESPONSE_CODE_UPDATE;
    }
}
