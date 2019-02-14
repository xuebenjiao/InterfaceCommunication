package com.example.binddemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MmkvActivity extends BaseActivity {
    public static final String TAG = MmkvActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       long start = System.currentTimeMillis();
      long  beforeMemory =  Runtime.getRuntime().totalMemory();
       for(int i =0;i<2000;i++){
           uMmkv.encode(String.valueOf(i),i);
       }
       Log.e(TAG,"MMKV 耗时："+(System.currentTimeMillis() - start)+"   ,占用内存："+(Runtime.getRuntime().totalMemory() - beforeMemory));

        SharedPreferences preferences = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        start = System.currentTimeMillis();
        beforeMemory =  Runtime.getRuntime().totalMemory();
        for(int i=0;i<2000;i++){
            editor.putInt(String.valueOf(i),i);
            editor.commit();
        }
        Log.e(TAG,"SharedPreferences 耗时："+(System.currentTimeMillis() - start)+"  ,占用内存："+(Runtime.getRuntime().totalMemory() - beforeMemory));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mmkv;
    }

}
