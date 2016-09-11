package ws.dyt.gank.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;

/**
 * Created by yangxiaowei on 16/9/5.
 */
public class Toast {
    public static void show(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, @StringRes int resId) {
        if (null == context) {
            return;
        }
        Toast.show(context, context.getResources().getString(resId));
    }
}
