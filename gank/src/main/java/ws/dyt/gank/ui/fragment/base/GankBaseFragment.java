package ws.dyt.gank.ui.fragment.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ws.dyt.gank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankBaseFragment extends Fragment implements IRefresh{


    public static final String PARAM_TITLE = "param_title";
    public GankBaseFragment() {
        // Required empty public constructor
    }

    public static Bundle newArgument(String title){
        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        return args;
    }


    @Override
    public void refresh() {

    }
}
