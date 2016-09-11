package ws.dyt.gank.ui.activity.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ws.dyt.gank.R;

/**
 * Created by yangxiaowei
 */
public class SingleFragmentActivity extends AppCompatActivity{
	private static final String PARAM_CLASS_FRAGMENT	 = "param_class_fragment";
	private static final String PARAM_TO_FRAGMENT		 = "param_to_fragment";
	//是否显示icon
	protected static final String PARAM_NAVIGATION_ICON	 = "param_navigation_icon";


	public static Intent generateIntent(Class classFragment, Bundle fragmentArgs) {
		return generateIntent(true, classFragment, fragmentArgs);
	}

	public static Intent generateIntent(boolean showNavigationIcon, Class classFragment, Bundle fragmentArgs) {
		Intent intent = new Intent();
		intent.putExtra(PARAM_NAVIGATION_ICON, showNavigationIcon);
		intent.putExtra(PARAM_CLASS_FRAGMENT, classFragment.getName());
		intent.putExtra(PARAM_TO_FRAGMENT, fragmentArgs);
		return intent;
	}

	public static void to(Context context, Class classFragment, Bundle args) {
		to(context, true, classFragment, args);
	}

	public static void to(Context context, boolean showNavigationIcon, Class classFragment, Bundle args) {
		to(context, SingleFragmentActivity.class, showNavigationIcon, classFragment, args);
	}

	public static void to(Context context, Class classActivity, Class classFragment, Bundle args) {
		to(context, classActivity, true, classFragment, args);
	}

	public static void to(Context context, Class classActivity, boolean showNavigationIcon, Class classFragment, Bundle args) {
		Intent intent = generateIntent(showNavigationIcon, classFragment, args);
		intent.setClass(context, classActivity);
		context.startActivity(intent);
	}

	public static void to() {}

	protected Fragment fragment = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(setContentViewLayout());

		if(null == savedInstanceState){
			String clazz = getIntent().getStringExtra(PARAM_CLASS_FRAGMENT);
			try {
				fragment = (Fragment) Class.forName(clazz).newInstance();
				fragment.setArguments(getIntent().getBundleExtra(PARAM_TO_FRAGMENT));
				getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "_fragment_").commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			fragment = getSupportFragmentManager().findFragmentByTag("_fragment_");
		}

		this.setupView();
	}

	//必须包含 R.id.container 作为Fragment容器
	@LayoutRes
	protected int setContentViewLayout() {
		return R.layout.container_with_no;
	}

	protected void setupView() {

	}

	@Override
	public void onBackPressed() {
		Fragment fragment = getFragment();
		boolean isFinish = true;
		if (fragment instanceof OnBackPreListener) {
			isFinish = ((OnBackPreListener) fragment).onBackPre();
		}

		if (isFinish) {
			super.onBackPressed();
		}
	}



	interface OnBackPreListener{
		/**
		 *
		 * @return true: finish
		 */
		boolean onBackPre();
	}

	public Fragment getFragment(){
		return fragment;
	}

}
