package ws.dyt.gank.ui.activity.base;


import android.support.v7.widget.Toolbar;
import android.view.View;

import ws.dyt.gank.R;

/**
 * Created by yangxiaowei
 */
public class SingleFragmentWithToolbarActivity extends SingleFragmentActivity{

	@Override
	protected int setContentViewLayout() {
		return R.layout.container_with_toolbar;
	}

	@Override
	protected void setupView() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (getIntent().getBooleanExtra(PARAM_NAVIGATION_ICON, true)) {
			toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
		}
		setSupportActionBar(toolbar);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}
}
