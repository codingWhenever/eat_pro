package com.eat.eatarms.net;

import com.eat.eatarms.BuildConfig;

/**
 *
 */
public class HttpURLAddress {
    private String URL_DEBUG;
    private String URL_RELEASE;
    private String URL_DEV;

    private static final class SingletonHolder {
        private static final HttpURLAddress INSTANCE = new HttpURLAddress();
    }

    public static HttpURLAddress getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getURL_DEBUG() {
        return URL_DEBUG;
    }

    public String getURL_RELEASE() {
        return URL_RELEASE;
    }

    public String getURL_DEV() {
        return URL_DEV;
    }

    public String getCurrentURL() {
        if (BuildConfig.DEBUG) {
            return getURL_DEBUG();
        }

        return getURL_RELEASE();
    }
}
