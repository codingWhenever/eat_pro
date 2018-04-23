package com.eat.orderpro.net;

import android.text.TextUtils;

import com.eat.eatarms.net.callback.BaseImpl;
import com.eat.eatarms.net.callback.BaseObserver;
import com.eat.orderpro.utils.EatToast;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

public abstract class EatBaseObserver<T> extends BaseObserver<T> {
    private boolean needDialog;
    private String titleMsg;

    public EatBaseObserver() {
        this(null, null);
    }

    public EatBaseObserver(BaseImpl baseImpl) {
        this(baseImpl, null);
    }

    public EatBaseObserver(BaseImpl base, String titleMsg) {
        super(base);
        this.titleMsg = titleMsg;
        if (TextUtils.isEmpty(titleMsg)) {
            needDialog = false;
        } else {
            needDialog = true;
        }
    }

    public EatBaseObserver(BaseImpl base, String titleMsg, boolean needDialog) {
        this(base, titleMsg);
        this.needDialog = needDialog;
    }

    @Override
    public String getTitleMsg() {
        return titleMsg;
    }

    @Override
    public boolean isNeedShowDialog() {
        return needDialog;
    }


    @Override
    public void onBaseError(Throwable e) {
        if (e instanceof HttpException) {
            handleException(ApiException.ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            handleException(ApiException.ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof JsonParseException ||
                e instanceof JSONException || e instanceof ParseException) {
            handleException(ApiException.ExceptionReason.PARSE_ERROR);
        } else if (e instanceof SocketTimeoutException) {
            handleException(ApiException.ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof InterruptedException) {
            handleException(ApiException.ExceptionReason.CONNECT_TIMEOUT);
        } else {
            handleException(ApiException.ExceptionReason.UNKNOWN_ERROR);
        }
    }

    /**
     * 统一处理异常信息
     *
     * @param exceptionReason
     */
    private void handleException(ApiException.ExceptionReason exceptionReason) {
        StringBuilder errorMsg = new StringBuilder();
        switch (exceptionReason) {
            case CONNECT_ERROR:
                errorMsg.append("网络连接失败");
                break;
            case CONNECT_TIMEOUT:
                errorMsg.append("网络连接超时");
                break;
            case PARSE_ERROR:
                errorMsg.append("数据解析异常");
                break;
            case BAD_NETWORK:
                errorMsg.append("网络不给力");
                break;
            case UNKNOWN_ERROR:
                errorMsg.append("服务器开小差啦");
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(errorMsg)) {
            EatToast.showToastSafe(errorMsg.toString());
        }
    }
}
