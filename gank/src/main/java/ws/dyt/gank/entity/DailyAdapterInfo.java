package ws.dyt.gank.entity;

import ws.dyt.view.adapter.ItemWrapper;

/**
 * Created by yangxiaowei on 16/9/3.
 *
 * 每日干货  适配器显示
 */
public class DailyAdapterInfo extends ItemWrapper<GankInfo>{
    //组名称
    public String groupTitle;

    public DailyAdapterInfo(int type, GankInfo data) {
        super(type, data);
    }

    public DailyAdapterInfo(int type, GankInfo data, String groupTitle) {
        this(type, data);
        this.groupTitle = groupTitle;
    }
}
