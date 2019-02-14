package com.example.binddemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.Set;

import butterknife.ButterKnife;

public abstract  class BaseActivity extends AppCompatActivity {
    public MMKV uMmkv;
final String mystr = "sdfsdfsf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        String fileStr = MMKV.initialize(this);
        uMmkv = MMKV.defaultMMKV();

    }

    public abstract  int getLayoutId();
}
