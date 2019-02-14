package com.example.binddemo;

import android.os.Bundle;

/*import com.example.library.EventBus;*/

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_second;
    }
    @OnClick(R.id.btn_posting)
    public void BtnPostClick(){
        new Thread("ThreadName") {
            @Override
            public void run() {
                EventBus.getDefault().post(new EventBusMessageEntity("Hello EventBus!"));
            }
        }.start();
        this.finish();

    }
    @OnClick(R.id.btn_async)
    public void BtnAsyncClick(){
        EventBus.getDefault().post(new EventBusMessageEntity("欢迎练习使用EventBus"));
        this.finish();
    }
    @OnClick(R.id.btn_main)
    public void BtnMainClick(){
        EventBus.getDefault().post(new EventBusMessageEntity("欢迎练习使用EventBus"));
    }

    @OnClick(R.id.btn_background)
    public void BtnBackgroundClick(){
        EventBus.getDefault().post(new EventBusMessageEntity("欢迎练习使用EventBus"));
    }

}
