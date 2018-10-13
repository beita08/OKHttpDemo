package com.mliuxb.okhttpdemo0923;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetSyncActivity extends AppCompatActivity {
    private static final String TAG = "GetSyncActivity";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sync);
        webView = findViewById(R.id.webView);

        okhttpGetSync();
    }

    //GET同步请求
    private void okhttpGetSync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.创建OkHttpClient对象
                    OkHttpClient okHttpClient = new OkHttpClient();
                    //2.创建Request对象
                    Request request = new Request.Builder().url("https://www.baidu.com/").build();
                    //3.得到响应对象
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        final String responseContent = response.body().string();
                        Log.i(TAG, "run: response.code() = " + response.code());
                        Log.i(TAG, "run: response.message() = " + response.message());
                        Log.i(TAG, "run: response.body().string() = " + responseContent);
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webView.loadData(responseContent, "text/html", "UTF-8");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
