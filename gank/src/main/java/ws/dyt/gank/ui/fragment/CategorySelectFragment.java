package ws.dyt.gank.ui.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.gank.R;
import ws.dyt.gank.utils.DipUtil;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * 列表选择
 */
public class CategorySelectFragment extends Fragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public CategorySelectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_select, container, false);
        ButterKnife.bind(this, view);
        this.init();
        return view;
    }

    private void init() {
        getActivity().setTitle(R.string.title_category);
        String[] category = getResources().getStringArray(R.array.title_category_gank);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new ItemDecoration());

        final SuperAdapter<String> adapter = new SuperAdapter<String>(getContext(), Arrays.asList(category), R.layout.item_category) {
            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_category, getItem(position));
            }
        };

        mRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new HeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                EventBus.getDefault().post(new GankCommitFragment.OnCategorySelectedEvent(adapter.getItem(position)));
                getActivity().finish();
            }
        });
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration{
        private int divider;

        public ItemDecoration() {
            divider = DipUtil.dip2px(getContext(), 0.5f);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            if (0 == position) {
                return;
            }
            outRect.top = divider;
        }
    }
}
