package ws.dyt.gank.utils;

import android.content.Context;

/**
 * Created by yangxiaowei on 16/7/22.
 */
public class PrivateLocalCache extends LocalCache{
    private static PrivateLocalCache instance = null;
    private static final Object lock = new Object();


    public static PrivateLocalCache newInstance(Context context) {
        if (null == instance) {
            synchronized (lock) {
                if (null == instance) {
                    instance = new PrivateLocalCache(context);
                }
            }
        }
        return  instance;
    }

    private PrivateLocalCache(Context context) {
        super(context);
    }

    @Override
    public String config() {
        return "gank_cache_info";
    }
}
