package ws.dyt.gank.entity;

/**
 * Created by yangxiaowei on 16/9/2.\
 *
 * 服务端数据响应格式
 */
public class Response<T> {
    public boolean error;
    public T results;
}
