package com.eat.eatarms.net.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * @author leo
 * @desc
 * @date
 */
public class ProgressDialogHandler extends Handler {
    public static final int SHOW_DIALOG = 1;
    public static final int DISMISS_DIALOG = 2;
    private Context context;
    private ProgressDialog dialog;
    private boolean cancelable;

    public ProgressDialogHandler(Context context, boolean cancelable) {
        super();
        this.context = context;
        this.cancelable = cancelable;
    }

    private void initProgressDialog(String title) {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            if (TextUtils.isEmpty(title)) {
                title = "加载中,请稍后....";
            }
            dialog.setMessage(title);
            dialog.setCancelable(cancelable);
            if (cancelable) {
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dismissProgressDialog();
                    }
                });
            }
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_DIALOG:
                initProgressDialog((String) msg.obj);
                break;
            case DISMISS_DIALOG:
                dismissProgressDialog();
                break;
            default:
                break;
        }
        super.handleMessage(msg);
    }
}
