package ws.dyt.pagelist.delegate;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import ws.dyt.pagelist.fragment.BasePageListFragment;
import ws.dyt.view.adapter.core.base.BaseAdapter;


/**
 * Created by yangxiaowei on 16/8/31.
 *
 * 必要的初始化设置
 *
 * @param <T_RESPONSE>  从数据源获取的数据实体类型
 * @param <T_ADAPTER>   适配器中显示的数据实体类型
 */
public interface InitConfig<T_RESPONSE, T_ADAPTER> {
    //默认的分页数据
    int PAGE_SIZE = 20;

    /**
     * 创建布局管理器
     * @return
     */
    RecyclerView.LayoutManager setLayoutManager();


    /**
     * 创建{@link RecyclerView} 的适配器
     * @return
     */
    BaseAdapter<T_ADAPTER> setAdapter();

    /**
     * 列表数据转换为适配器数据
     * 因为有的时候可能列表数据需要进行多个数据源的整合, 但是分页还是一个来源
     *
     * @param datas
     * @return
     */
    List<T_ADAPTER> convert(List<T_RESPONSE> datas);

    /**
     * 设置每次请求的数据量
     * @return
     */
    int setPageSize();

    /**
     * 向数据源加载数据(可以是任何地方)
     *
     * 调用该方法将会加载新数据, 比如从网络加载数据, 会有成功和失败的情况,
     * 状态返回后需要手动调用
     *  {@link ResponseResultDelegate#setOnSuccessPath(BasePageListFragment.ResponseResultWrapper),
     *   @link ResponseResultDelegate#setOnFailurePath()
     *  }来设置数据显示
     *
     * @param pageIndex 上一页最后一条数据分页索引
     */
    void sendRequest(int pageIndex);

    /**
     * 其他的一些ui操作
     */
    void setUpView();
}
