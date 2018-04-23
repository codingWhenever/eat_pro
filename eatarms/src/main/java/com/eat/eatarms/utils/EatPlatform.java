package com.eat.eatarms.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author leo
 */
public class EatPlatform {
    private static final EatPlatform EAT_PLATFORM = findPlatform();

    public static EatPlatform get() {
        return EAT_PLATFORM;
    }

    private static EatPlatform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new EatPlatform();
    }

    public Executor defaultCallbackExecutor() {
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable) {
        defaultCallbackExecutor().execute(runnable);
    }

    static class Android extends EatPlatform {
        @Override
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor {

            @Override
            public void execute(@NonNull Runnable command) {
                new Handler(Looper.getMainLooper()).post(command);
            }
        }
    }
}
