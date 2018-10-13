package com.mliuxb.okhttpdemo0923;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostSyncActivity extends AppCompatActivity {
    private static final String TAG = "PostSyncActivity";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sync);
        webView = findViewById(R.id.webView);

        okhttpPostSync();
    }

    //POST同步请求
    private void okhttpPostSync() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();

                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("username", "mliuxb");
                    builder.add("password", "123456");
                    Request request = new Request.Builder().url("http://www.wanandroid.com/user/login").post(builder.build()).build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        final String responseContent = response.body().string();
                        Log.i(TAG, "run: response.code() = " + response.code());
                        Log.i(TAG, "run: response.message() = " + response.message());
                        Log.i(TAG, "run: response.body().string() = " + responseContent);
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        JSONObject jsonObject = new JSONObject();
                        int errorCode = jsonObject.optInt("errorCode");
                        Log.i(TAG, "run: errorCode = " + errorCode);
                        if (errorCode == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PostSyncActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "run: e = "+ e.getMessage());
                }
            }
        }).start();
    }
}
