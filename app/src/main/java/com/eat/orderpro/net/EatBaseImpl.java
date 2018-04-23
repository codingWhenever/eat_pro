package com.eat.orderpro.net;

import com.eat.eatarms.net.callback.BaseImpl;

public abstract class EatBaseImpl<T> extends BaseImpl<T> {
    abstract void onSuccess(T data);

    abstract void onFailure(String message);

    void onUpdate(T data) {

    }

    void onInvalidToken() {

    }
}
