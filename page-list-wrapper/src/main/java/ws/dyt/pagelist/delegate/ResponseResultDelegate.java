package ws.dyt.pagelist.delegate;

import ws.dyt.pagelist.entity.PageIndex;
import ws.dyt.pagelist.fragment.BasePageListFragment;

/**
 * Created by yangxiaowei on 16/7/9.
 */
public interface ResponseResultDelegate<T extends PageIndex, M> {
    /**
     * @param result
     */
    void setOnSuccessPath(BasePageListFragment<T, M>.ResponseResultWrapper<T> result);

    /**
     *
     */
    void setOnFailurePath();
}
