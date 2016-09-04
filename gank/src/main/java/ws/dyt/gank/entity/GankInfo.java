package ws.dyt.gank.entity;

import ws.dyt.pagelist.entity.PageIndex;

/**
 * Created by yangxiaowei on 16/9/2.
 *
 * 数据描述类
 */
public class GankInfo implements PageIndex{
    @Override
    public int getPageIndex() {
        return 0;
    }

    public String _id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public String used;
    public String who;
}
