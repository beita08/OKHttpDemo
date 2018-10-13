package com.mliuxb.okhttpdemo0923;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_get_sync, R.id.btn_get_async, R.id.btn_post_sync, R.id.btn_post_async})
    public void onViewClicked(View view) {
        if (mIntent == null) {
            mIntent = new Intent();
        }
        switch (view.getId()) {
            case R.id.btn_get_sync:
                mIntent.setClass(this, GetSyncActivity.class);
                break;
            case R.id.btn_get_async:
                mIntent.setClass(this, GetAsyncActivity.class);
                break;
            case R.id.btn_post_sync:
                mIntent.setClass(this,PostSyncActivity.class);
                break;
            case R.id.btn_post_async:
                mIntent.setClass(this,PostAsyncActivity.class);
                break;
        }
        startActivity(mIntent);
    }
}
