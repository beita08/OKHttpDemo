package com.mliuxb.okhttpdemo0923;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostAsyncActivity extends AppCompatActivity {
    private static final String TAG = "PostAsyncActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_async);

        okhttpPostAsync();
    }

    ////POST异步请求
    private void okhttpPostAsync() {
        Log.w(TAG, "okhttpPostAsync: 111111111");
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("username", "mliuxb");
        builder.add("password", "123456");

        Request request = new Request.Builder().url("https://www.wanandroid.com/user/login").post(builder.build()).build();

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

                    JSONObject jsonObject = new JSONObject();
                    int errorCode = jsonObject.optInt("errorCode");
                    Log.w(TAG, "run: errorCode = " + errorCode);
                    if (errorCode == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PostAsyncActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Log.w(TAG, "onResponse: isFail");
                }
            }
        });
    }

}
