package ws.dyt.gank.net;

import java.util.ArrayList;

import ws.dyt.gank.entity.DailyResponseInfo;
import ws.dyt.gank.entity.GankInfo;
import ws.dyt.gank.entity.Response;
import retrofit.Callback;

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
}
