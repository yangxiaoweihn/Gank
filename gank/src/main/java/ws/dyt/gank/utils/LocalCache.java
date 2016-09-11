package ws.dyt.gank.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yangxiaowei on 16/7/22.
 */
abstract
public class LocalCache {
    private SharedPreferences sp = null;

    public LocalCache(Context context) {
        if (null == sp) {
            sp = context.getSharedPreferences(config(), Context.MODE_PRIVATE);
        }
    }

    abstract
    public String config();

//    public <V> void put(String key, V value) {
//
//    }
//
//    public <V> V get(String key, V defaultV) {
//
//    }

    public void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultV) {
        return sp.getString(key, defaultV);
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defaultV) {
        return sp.getInt(key, defaultV);
    }

    public void putLong(String key, int value) {
        sp.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defaultV) {
        return sp.getLong(key, defaultV);
    }

    public void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key, float defaultV) {
        return sp.getFloat(key, defaultV);
    }

    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultV) {
        return sp.getBoolean(key, defaultV);
    }

    public void clear(String key) {
        sp.edit().clear().apply();
    }
}
