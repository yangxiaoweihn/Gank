package ws.dyt.gank.utils;

import android.text.TextUtils;

/**
 * Created by yangxiaowei on 16/9/4.
 */
public class UriUtils {
    public static String getParamValue(String url, String key){
        if (TextUtils.isEmpty(url) || !url.contains("?") || !url.contains("=")) {
            return null;
        }
        url = url.substring(url.indexOf("?")+1);
        String[] kv = url.split("&");
        if (null == kv || kv.length == 0) {
            return null;
        }
        for (String e:kv) {
            String[] item = e.split("=");
            if (null != item && item.length == 2) {
                if (item[0].equals(key)) {
                    return item[1];
                }
            }
        }
        return null;
    }

    private String newGankGetUrl(String url) {
        String newUrl = "";
        if (TextUtils.isEmpty(url) || !url.contains("?")) {
            return newUrl;
        }

        newUrl = url.substring(0, url.indexOf("?"));
        newUrl += findAndAppendParamValue(url);
        return newUrl;
    }
    /**
     *
     * @param url
     * @return  /aa/aa/aa
     */
    private String findAndAppendParamValue(String url) {
        String parms = "";
        if (TextUtils.isEmpty(url) || !url.contains("?")) {
            return parms;
        }
        url = url.substring(url.indexOf("?"));
        if (TextUtils.isEmpty(url)) {
            return parms;
        }

        String[] pair = url.split("&");
        if (null == pair || pair.length == 0) {
            return parms;
        }

        for (String e:pair) {
            String[] kv = e.split("=");
            if (null != kv && kv.length == 2) {
                String value = kv[1];
                parms += "/"+value;
            }
        }
        return parms;
    }
}
