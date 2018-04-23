package com.eat.orderpro.application;

import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;

/**
 * @author leo
 */
public class EatApplication extends MultiDexApplication {
    private static EatApplication sInstance;
    public static int mMainThreadId = -1;
    public static Thread mMainThread;
    public static Handler mMainThreadHandler;
    public static Looper mMainLooper;

    @Override
    public void onCreate() {
        super.onCreate();
        initThread();
    }

    private void initThread() {
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myPid();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static EatApplication getInstance() {
        return sInstance;
    }

    public EatApplication() {
        sInstance = this;
    }
}
