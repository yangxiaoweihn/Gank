package ws.dyt.gank.net;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by yangxiaowei on 16/9/2.
 */
public class BaseRestApi {
    protected Retrofit mRetrofit;
    protected IRestApi mRestApi;


    public BaseRestApi() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetConfig.ApiGank)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();

        mRestApi = mRetrofit.create(IRestApi.class);

    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                 String url = chain.request().url().toString();
                //以下的header信息是让github授权时返回json数据
                Response response = chain.proceed(chain.request().newBuilder().addHeader("Accept", "application/json").url(url).build());
                return response;
            }
        });
        return client;
    }


}
