package ws.dyt.gank.net;

import java.util.ArrayList;

import ws.dyt.gank.entity.DailyResponseInfo;
import ws.dyt.gank.entity.GankInfo;
import ws.dyt.gank.entity.Response;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by yangxiaowei on 16/9/2.
 */
public interface IRestApi {
    @GET("data")
    Call<Response<ArrayList<GankInfo>>> getCagetoryList(@Query(value = "category") String category, @Query(value = "pageSize") int pageSize, @Query(value = "page") int page);

    @GET("day")
    Call<Response<DailyResponseInfo>> getDailyGankInfo(@Query(value = "year") int year, @Query(value = "month") int month, @Query(value = "day") int day);

}
