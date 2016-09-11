package ws.dyt.gank;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;

import ws.dyt.gank.entity.GithubUserInfo;
import ws.dyt.gank.utils.PrivateLocalCache;

/**
 * Created by yangxiaowei on 16/9/5.
 */
public class GankApplication extends Application{
    private static GankApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static GithubUserInfo getUserInfo(){
        String json = PrivateLocalCache.newInstance(application.getApplicationContext()).getString("github_userinfo", null);
        if (TextUtils.isEmpty(json)) {
            return new GithubUserInfo();
        }

        return new Gson().fromJson(json, GithubUserInfo.class);
    }

    public static void saveUserInfo(GithubUserInfo info) {
        if (null == info) {
            PrivateLocalCache.newInstance(application.getApplicationContext()).clear("github_userinfo");
            return;
        }

        PrivateLocalCache.newInstance(application.getApplicationContext()).putString("github_userinfo", new Gson().toJson(info, GithubUserInfo.class));
    }
}
