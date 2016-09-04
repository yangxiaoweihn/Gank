package ws.dyt.gank.ui.fragment;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ws.dyt.gank.R;
import ws.dyt.gank.utils.DipUtil;

/**
 * 分类列表数据-福利
 */
public class GankCategoryMeiZhiListFragment extends GankCategoryListFragment {


    public GankCategoryMeiZhiListFragment() {
        // Required empty public constructor
    }

    public static GankCategoryMeiZhiListFragment newInstance(String category) {
        GankCategoryMeiZhiListFragment fragment = new GankCategoryMeiZhiListFragment();
        fragment.setArguments(newArgs(category));
        return fragment;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_gank_category_fuli;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new ItemDecoration();
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        private final int divider;
        public ItemDecoration() {
            divider = DipUtil.dip2px(getContext(), 10f);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            if (0 == position) {
                return;
            }

            outRect.top = divider;
//            outRect.right = 10;
        }
    }
}
