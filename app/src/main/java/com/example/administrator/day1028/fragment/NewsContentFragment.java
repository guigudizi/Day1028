package com.example.administrator.day1028.fragment;


import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.day1028.R;
import com.example.administrator.day1028.activities.BorwserActivity;
import com.example.administrator.day1028.base.BaseFragment;
import com.example.administrator.day1028.biz.Xhttp;

import butterknife.BindView;


public class NewsContentFragment extends BaseFragment {



    @BindView(R.id.web_view1)
    WebView mWebView1;
    private String mdocId;


    public NewsContentFragment() {

    }


    public static NewsContentFragment newInstance(String docId) {
        NewsContentFragment fragment = new NewsContentFragment();
        Bundle args = new Bundle();
        args.putString(BorwserActivity.KEY_DOCID, docId);

        fragment.setArguments(args);
        return fragment;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mdocId = getArguments().getString(BorwserActivity.KEY_DOCID);

        }
    }


    @Override
    protected void initData() {
        //初始化数据
        //从网络获取json，解析，显示
        //webview的基本设置
        /**
         * webview设置，允许javaScript脚本
         */
        mWebView1.getSettings().setJavaScriptEnabled(true);
        /**
         * 让图片自适应我们的屏幕尺寸, webview 本身可以设置setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)实现;
         但是!!! 该方法只支持4.4之前的版本.4.4以后的就不起作用了.为了能兼容所有该版本.我们通过 webview 与js 交互的方式来实现。
         */
        mWebView1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        /**
         *    //shouldOverrideUrlLoading返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
         */
        mWebView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }


            /**
             *   //重新设置图片大小，适配当前的屏幕
             *
             *
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                imgReset();
            }
        });

        /**
         * 通过工具类获取对应docid的json，并解析成string交给webview显示。
         */
        Xhttp.getNewsContent(mdocId, new Xhttp.NewsContentListener() {
            @Override
            public void onFinish(String str) {
                mWebView1.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_content;
    }
    private void imgReset() {
        mWebView1.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%';   " +
                "}" +
                "})()");
    }

}
