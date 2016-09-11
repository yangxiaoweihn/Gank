package ws.dyt.gank.net;

import java.util.ArrayList;

import retrofit.http.POST;
import retrofit.http.Path;
import ws.dyt.gank.entity.DailyResponseInfo;
import ws.dyt.gank.entity.GankInfo;
import ws.dyt.gank.entity.GithubToken;
import ws.dyt.gank.entity.GithubUserInfo;
import ws.dyt.gank.entity.Null;
import ws.dyt.gank.entity.Response;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import ws.dyt.gank.ui.fragment.github.GithubConfig;

/**
 * Created by yangxiaowei on 16/9/2.
 */
public interface IRestApi {
    @GET("data/{category}/{pageSize}/{page}")
    Call<Response<ArrayList<GankInfo>>> getCagetoryList(
            @Path(value = "category") String category,
            @Path(value = "pageSize") int pageSize,
            @Path(value = "page") int page
    );

    @GET("day/{year}/{month}/{day}")
    Call<Response<DailyResponseInfo>> getDailyGankInfo(
            @Path(value = "year") int year,
            @Path(value = "month") int month,
            @Path(value = "day") int day
    );

    @POST(GithubConfig.NetGithub +"login/oauth/access_token")
    Call<GithubToken> getGithubTokenInfo(
            @Query(value = "client_id") String clentId,
            @Query(value = "client_secret") String clientSecret,
            @Query(value = "code") String code,
            @Query(value = "redirect_uri") String redirectUri
    );

    @GET(GithubConfig.ApiGithub +"user")
    Call<GithubUserInfo> getGithubUserInfo(
            @Query(value = "access_token") String accessToken
    );


    @POST("add2gank")
    Call<Response<Null>> commitGankInfo(
            @Query(value = "debug") boolean debug,
            @Query(value = "who") String userId,
            @Query(value = "type") String category,
            @Query(value = "url") String gankUrl,
            @Query(value = "desc") String gankDesc
    );
}
