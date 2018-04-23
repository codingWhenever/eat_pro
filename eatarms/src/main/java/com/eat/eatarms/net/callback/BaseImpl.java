package com.eat.eatarms.net.callback;

import android.content.Context;

import io.reactivex.disposables.Disposable;

/**
 * @author leo
 */
public abstract class BaseImpl<T> {
    abstract boolean addDisposable(Disposable disposable);

    abstract Context getContext();


}
