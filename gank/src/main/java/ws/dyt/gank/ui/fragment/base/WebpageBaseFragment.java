package ws.dyt.gank.ui.fragment.base;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.gank.R;

/**
 */
public class WebpageBaseFragment extends GankBaseFragment {


    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.webView)
    WebView mWebView;

    public WebpageBaseFragment() {
        // Required empty public constructor
    }

    private static final String PARAM_URL = "param_url";

    public static Bundle generateArgs(String title, String url) {
        Bundle args = newArgument(title);
        args.putString(PARAM_URL, url);
        return args;
    }

    public static WebpageBaseFragment newInstance(String title, String url) {
        WebpageBaseFragment fragment = new WebpageBaseFragment();
        fragment.setArguments(generateArgs(title, url));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webpage_base, container, false);
        ButterKnife.bind(this, view);
        this.initView(view, false);
        return view;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @JavascriptInterface
    private void initView(View rootView, boolean scrollbars) {
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mWebView = (WebView) rootView.findViewById(R.id.webView);

        this.onReceivedTitle(getArguments().getString(PARAM_TITLE));

        if (!scrollbars) {
            mWebView.setHorizontalScrollBarEnabled(false);
            mWebView.setVerticalScrollBarEnabled(false);
        }
        WebSettings settings = mWebView.getSettings();
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        //支持javascript
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 11) {
            settings.setDisplayZoomControls(false);
        }
        settings.setDomStorageEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        try {
            mWebView.setWebViewClient(webViewClient);

        } catch (Exception e) {

            e.printStackTrace();
        }


        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                mProgressBar.setProgress(progress);
                if (progress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                WebpageBaseFragment.this.onReceivedTitle(title);

            }
        });
        String url = getArguments().getString(PARAM_URL);
        if (!TextUtils.isEmpty(url)) {

            Logger.e("webpageNotNativeFragment", "url: " + url);
            if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("file://")) {

                mWebView.loadUrl(url);
            } else {
                mWebView.loadDataWithBaseURL("", url, "text/html", "UTF-8", "");
            }
        }
    }

    protected void onReceivedTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        getActivity().setTitle(title);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mWebView.removeAllViews();
        mWebView.destroy();
        ((ViewGroup) mWebView.getParent()).removeAllViews();

    }


    public void goBack() {

        mWebView.goBack();
    }


    //
    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            mProgressBar.setVisibility(View.VISIBLE);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //清空缓存，保证获取最新数据(后台数据更新可通过更改Url的方式,http://test.php?update=2 "update"用于更改路径)
            view.clearCache(true);

        }
    };
}
