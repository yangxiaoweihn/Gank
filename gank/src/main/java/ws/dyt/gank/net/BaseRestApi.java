package ws.dyt.gank.net;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
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

    //默认超时时间10秒
    private static final long DEFAULT_TIMEOUT = 20 * 1000;


    public BaseRestApi() {

        mRetrofit = new Retrofit.Builder()
//                .client(generateOkHttpClient())
                .baseUrl(NetConfig.API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mRestApi = mRetrofit.create(IRestApi.class);

    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                 String url = chain.request().url().toString();


                //gank api 的方式必须进行转化才行
                if ("GET".equals(chain.request().method())) {
                    url = newUrl(url);
                    Logger.e("DEBUG", "---: "+url);

                }
                Response response = chain.proceed(chain.request().newBuilder().url(url).build());
                return response;
            }
        });
        return client;
    }

    private String newUrl(String url) {
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
