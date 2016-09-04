package ws.dyt.gank.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaowei on 16/9/3.
 *
 * 每日干货
 */
public class DailyResponseInfo {
    @SerializedName("Android")
    public ArrayList<GankInfo> android;

    @SerializedName("iOS")
    public ArrayList<GankInfo> ios;

    @SerializedName("瞎推荐")
    public ArrayList<GankInfo> recommend;

    @SerializedName("拓展资源")
    public ArrayList<GankInfo> extendRes;

    @SerializedName("福利")
    public ArrayList<GankInfo> meiZhi;

    @SerializedName("休息视频")
    public ArrayList<GankInfo> restVideo;


    public boolean isContainsMeiZhi() {
        return null != meiZhi && !meiZhi.isEmpty();
    }

    public List<GankInfo> get() {
        List[] gankInfos = {/*meiZhi, */android, ios, recommend, extendRes, restVideo};
        List<GankInfo> all = new ArrayList<>();
        for (List e:gankInfos) {
            if (null == e || e.isEmpty()) {
                continue;
            }

            all.addAll(e);
        }
        return all;
    }

}
