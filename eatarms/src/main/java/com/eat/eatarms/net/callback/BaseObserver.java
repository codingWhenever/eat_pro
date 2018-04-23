package com.eat.eatarms.net.callback;

import android.text.TextUtils;
import android.util.Log;

import com.eat.eatarms.utils.Constant;
import com.eat.eatarms.utils.EatPlatform;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @param <T>
 * @author leo
 */
public abstract class BaseObserver<T> implements Observer<T> {
    private static final String TAG = "BaseObserver";

    private BaseImpl baseImpl;
    private ProgressDialogHandler progressDialogHandler;
    private EatPlatform platform;

    public BaseObserver(BaseImpl baseImpl) {
        this.baseImpl = baseImpl;
        if (null != baseImpl) {
            if (null == progressDialogHandler) {
                progressDialogHandler = new ProgressDialogHandler(baseImpl.getContext(), true);
            }
        }
        platform = EatPlatform.get();
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (isNeedShowDialog()) {
            showProgressDialog();
        }
        if (null != baseImpl && null != d) {
            baseImpl.addDisposable(d);
        }
    }

    @Override
    public void onNext(final T t) {
        Log.d(Constant.EAT_TAG, t.toString());
        if (null != t) {
            platform.execute(new Runnable() {
                @Override
                public void run() {
                    onBaseNext(t);
                }
            });
        }
    }

    @Override
    public void onError(final Throwable e) {
        Log.e(Constant.EAT_TAG, e.getMessage());
        if (isNeedShowDialog()) {
            dismissProgressDialog();
        }
        platform.execute(new Runnable() {
            @Override
            public void run() {
                onBaseError(e);

            }
        });
    }

    @Override
    public void onComplete() {
        if (isNeedShowDialog()) {
            dismissProgressDialog();
        }
    }



    /**
     * 获取对话框标题
     *
     * @return
     */
    public abstract String getTitleMsg();

    /**
     * 是否需要显示dialog
     *
     * @return
     */
    public abstract boolean isNeedShowDialog();

    private void showProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_DIALOG, getTitleMsg()).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (null != progressDialogHandler) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_DIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }

    public abstract void onBaseNext(T t);

    public abstract void onBaseError(Throwable t);

}
