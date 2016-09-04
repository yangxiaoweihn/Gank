package ws.dyt.view.adapter.section;

import android.support.annotation.LayoutRes;

public interface SectionMultiItemViewType {
    @LayoutRes
    int getSectionDataItemViewLayout(int group, int position);

    @LayoutRes
    int getSectionHeaderItemViewLayout(int group/*, int position*/);

    @LayoutRes
    int getSectionFooterItemViewLayout(int group/*, int position*/);
}