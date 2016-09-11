package ws.dyt.gank.net;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Query;
import ws.dyt.gank.entity.DailyResponseInfo;
import ws.dyt.gank.entity.GankInfo;
import ws.dyt.gank.entity.GithubToken;
import ws.dyt.gank.entity.GithubUserInfo;
import ws.dyt.gank.entity.Null;
import ws.dyt.gank.entity.Response;
import retrofit.Callback;
import ws.dyt.gank.ui.fragment.github.GithubConfig;

/**
 * Created by yangxiaowei on 16/9/2.
 */
public class RestApi extends BaseRestApi{
    private RestApi() {
        super();
    }

    private static RestApi instance = null;

    public static RestApi getInstance() {
        synchronized (RestApi.class) {
            if (null == instance) {
                synchronized (RestApi.class) {
                    instance = new RestApi();
                }
            }
        }
        return instance;
    }


    public void getCagetoryList(String category, int pageSize, int page, Callback<Response<ArrayList<GankInfo>>> callback) {
        mRestApi.getCagetoryList(category, pageSize, page).enqueue(callback);
    }

    public void getDailyGankInfo(int year, int month, int day, Callback<Response<DailyResponseInfo>> callback) {
        mRestApi.getDailyGankInfo(year, month, day).enqueue(callback);
    }

    public void getGithubTokenInfo(String code, Callback<GithubToken> callback) {
        mRestApi.getGithubTokenInfo(
                GithubConfig.ClientId,
                GithubConfig.ClientSecret,
                code,
                GithubConfig.RedirectUri
        ).enqueue(callback);
    }

    public void getGithubUserInfo(String accessToken, Callback<GithubUserInfo> callback) {
        mRestApi.getGithubUserInfo(accessToken).enqueue(callback);
    }

    public void commitGankInfo(boolean debug, String userId, String category, String gankUrl, String gankDesc, Callback<Response<Null>> callback){
        mRestApi.commitGankInfo(
                debug,
                userId,
                category,
                gankUrl,
                gankDesc
        ).enqueue(callback);
    }
}
