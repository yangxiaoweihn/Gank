package ws.dyt.gank.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ws.dyt.gank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankCommitFragment extends Fragment {


    public GankCommitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank_commit, container, false);
    }

}
