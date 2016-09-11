package ws.dyt.gank.utils;

import android.app.Dialog;
import android.content.Context;

import ws.dyt.gank.R;

/**
 * Created by yangxiaowei on 16/9/9.
 */
public class DialogUtils {
    private static DialogUtils instance = null;

    private DialogUtils(){
    }

    public static DialogUtils newInstance() {
        if (null == instance) {
            synchronized (DialogUtils.class) {
                instance = new DialogUtils();
            }
        }
        return instance;
    }

    private Dialog dialog;
    public Dialog showLoadingDialog(Context context) {
        dismissLoadingDialog();
        dialog = new Dialog(context, R.style.LoadingDialog);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.show();
        return dialog;
    }

    public void dismissLoadingDialog() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }
}
