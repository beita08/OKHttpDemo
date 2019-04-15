package com.mliuxb.okhttpdemo0923;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetAsyncActivity extends AppCompatActivity {
    private static final String TAG = "GetAsyncActivity";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_async);
        webView = findViewById(R.id.webView);

        okhttpGetAsync();
    }

    //GET异步请求
    private void okhttpGetAsync() {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("https://www.baidu.com/").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.w(TAG, "onFailure: call =" + call.toString() + ", e = " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseContent = response.body().string();
                    Log.w(TAG, "run: response.code() = " + response.code());
                    Log.w(TAG, "run: response.message() = " + response.message());
                    Log.w(TAG, "run: response.body().string() = " + responseContent);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadData(responseContent, "text/html", "UTF-8");
                        }
                    });
                } else {
                    Log.w(TAG, "onResponse: fail");
                }
            }
        });
    }
}



