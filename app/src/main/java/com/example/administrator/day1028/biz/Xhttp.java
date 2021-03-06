package com.example.administrator.day1028.biz;

import android.util.Log;

import com.example.administrator.day1028.commom.CommonUrls;
import com.example.administrator.day1028.entity.NetEase;
import com.example.administrator.day1028.entity.NewsContent;
import com.example.administrator.day1028.entity.NewsType;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class Xhttp {

    private static final String TAG = "xhttp";

    public static void getNewsList(String uri, final String tid, final OnSuccessListener listener) {
        RequestParams entity = new RequestParams(uri);
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ArrayList<NetEase> neteaseNews = new ArrayList<>();
                Gson gson = new Gson();
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray array = obj.getJSONArray(tid);
                    for (int i = 0; i < array.length(); i++) {
                        NetEase netEase = gson.fromJson(array.get(i).toString(), NetEase.class);
                        neteaseNews.add(netEase);
                    }
                    //使用接口
                    if (listener != null) {
                        listener.setNewsList(neteaseNews);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: " + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished: ");
            }
        };
        x.http().get(entity, callback);
    }


    public interface OnSuccessListener {
        void setNewsList(List<NetEase> neteaseNews);
    }

    public static void getNewsTypeList(final NewsTypeListener lis) {
        RequestParams en = new RequestParams(CommonUrls.NEWS_TYPEURL);

        Callback.CommonCallback<String> cl = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "onSuccess" + result);
                Gson gson = new Gson();
                NewsType newsType = gson.fromJson(result, NewsType.class);
                Log.d(TAG, newsType.tList.get(0).tname + "");
                if (lis != null) {
                    lis.onSuccess(newsType);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished");
                if (lis != null) {
                    lis.onFinish();
                }
            }
        };
        x.http().get(en, cl);
    }

    public static interface NewsTypeListener {
        void onSuccess(NewsType newsType);

        void onFinish();
    }

    public static void getNewsContent(final String docId, final NewsContentListener listener) {

        RequestParams entity = new RequestParams(CommonUrls.getNewsContUrl(docId));
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String string = obj.getString(docId);
                    Gson gson = new Gson();
                    NewsContent newsContent = gson.fromJson(string, NewsContent.class);

                    //从newsContent对象中把body和img集合重新整合一个让webview显示的string

                    String before = "<p><img src=\"";
                    String after = "\"/> </img></p>";
                    //重整字符串：
                    //1.添加标题；<p><h1>  </h1></p>
                    String title_b = "<p><h2>";
                    String title_a = "</h2></p>";

                    newsContent.body = title_b + newsContent.title + title_a + newsContent.body;
                    //添加作者：
                    for (NewsContent.Img img : newsContent.img) {
                        newsContent.body = newsContent.body.replace(img.ref, before + img.src + after);
                    }

                    //字符串交给webview
                    if (listener != null) {
                        listener.onFinish(newsContent.body);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onSuccess解析异常: " + e.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished: ");

            }
        };
        x.http().get(entity, callback);

    }

    public interface NewsContentListener {
        void onFinish(String str);
    }
}
