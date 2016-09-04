package ws.dyt.gank;

import android.os.Bundle;

import ws.dyt.gank.ui.activity.base.SingleFragmentActivity;
import ws.dyt.gank.ui.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIntent(generateIntent(MainFragment.class, null));
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }
}
