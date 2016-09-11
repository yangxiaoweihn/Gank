package ws.dyt.gank.ui.fragment.github;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import retrofit.Callback;
import retrofit.Response;
import ws.dyt.gank.GankApplication;
import ws.dyt.gank.entity.GithubToken;
import ws.dyt.gank.entity.GithubUserInfo;
import ws.dyt.gank.net.RestApi;
import ws.dyt.gank.ui.fragment.UserCenterFragment;
import ws.dyt.gank.ui.fragment.base.WebpageBaseFragment;
import ws.dyt.gank.utils.DialogUtils;
import ws.dyt.gank.utils.Toast;
import ws.dyt.gank.utils.UriUtils;

/**
 * A simple {@link Fragment} subclass.
 *
 * github 授权
 */
public class GithubAuthFragment extends WebpageBaseFragment {


    public GithubAuthFragment() {
        // Required empty public constructor
    }

    private static final String PARAM_FLAG_IN = "param_flag_in";
    public static Bundle generateArgs(int flag) {
        //1.认证
        String url = GithubConfig.NetGithub +"/login/oauth/authorize?client_id=" +
                GithubConfig.ClientId+
                "&state=00&redirect_uri="+
                GithubConfig.RedirectUri;

        Bundle args = newArgument("认证");
        args.putString(PARAM_URL, url);
        args.putInt(PARAM_FLAG_IN, flag);
        return args;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        //拦截回调地址
        if (url.startsWith(GithubConfig.RedirectUri)) {
            Logger.e("Gank-GGGGGG", "url: " + url);

            if (url.contains("code=")) {
                String code = UriUtils.getParamValue(url, "code");

                //2. 获取token
                this.getGithubTokenInfo(code);
                return true;
            }
        }
        return false;
    }


    private void getGithubTokenInfo(String code) {
        DialogUtils.newInstance().showLoadingDialog(getContext());
        RestApi.getInstance().getGithubTokenInfo(code, new Callback<GithubToken>() {
            @Override
            public void onResponse(Response<GithubToken> response) {
                DialogUtils.newInstance().dismissLoadingDialog();
                String token = null == response ? null : (null == response.body() ? null : response.body().access_token);
                if (TextUtils.isEmpty(token)) {
                    Toast.show(getContext(), "Token获取错误");
                    getActivity().finish();
                    return;
                }
                Logger.e("Gank-GGGGGG", response.body().toString());
                //3. 获取用户信息
                getGithubUserInfo(response.body().access_token);
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtils.newInstance().dismissLoadingDialog();
                t.printStackTrace();
                Toast.show(getContext(), "错误");
                getActivity().finish();
            }
        });
    }

    private void getGithubUserInfo(String token) {
        DialogUtils.newInstance().showLoadingDialog(getContext());
        RestApi.getInstance().getGithubUserInfo(token, new Callback<GithubUserInfo>() {
            @Override
            public void onResponse(Response<GithubUserInfo> response) {
                DialogUtils.newInstance().dismissLoadingDialog();
                Logger.e("Gank-GGGGGG", response.body().toString());

                GankApplication.saveUserInfo(response.body());
                EventBus.getDefault().post(new OnAuthOkEvent(getArguments().getInt(PARAM_FLAG_IN, 0)));
                getActivity().finish();
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtils.newInstance().dismissLoadingDialog();
                Toast.show(getContext(), "错误");
                getActivity().finish();
            }
        });
    }


    public static class OnAuthOkEvent{
        //传递进来的标识id
        public int flag;

        public OnAuthOkEvent(int flag) {
            this.flag = flag;
        }
    }
}
