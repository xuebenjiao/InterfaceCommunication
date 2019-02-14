package com.example.binddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interfacefunction.FunctionManager;
import com.example.interfacefunction.FunctionNoParamNoResult;
import com.example.interfacefunction.FunctionWithParamAndResult;
import com.example.interfacefunction.FunctionWithParamOnly;
import com.example.interfacefunction.FunctionWithResultOnly;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/*import com.example.library.EventBus;
import com.example.library.Subscribe;
import com.example.library.ThreadMode;*/

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getName();
    private FunctionManager functionManager;
    @BindView(R.id.tv_posting)
    TextView tv_posting;
    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_sync)
    TextView tv_sync;
    @BindView(R.id.tv_background)
    TextView tv_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        init();
        ThreadLocal<String> local = new ThreadLocal<String>(){
            @Override
            protected String initialValue() {
                return super.initialValue();
            }
        };
    }
    @OnClick(R.id.btn_jump_to_second)
    public void jumpToSecondActivity(){
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }

    @Override
    public int getLayoutId() {
        return   R.layout.activity_main;
    }

    private void init(){

        functionManager = FunctionManager.getInstance();
        functionManager.addFunction( new FunctionNoParamNoResult(BlankFragmentA.FUNCTION_NAME) {
            @Override
            public void function() {
                Toast.makeText(MainActivity.this,"无参无返回值",Toast.LENGTH_LONG).show();
            }
        }).addFunction(new FunctionWithParamOnly<String>(BlankFragmentA.FUNCTION_NAME){
            @Override
            public void function(String o) {
                Toast.makeText(MainActivity.this,"有参无返回值（参数为）："+o,Toast.LENGTH_LONG).show();
            }
        }).addFunction(new FunctionWithResultOnly<String>(BlankFragmentA.FUNCTION_NAME) {
            @Override
            public String function() {
                return "返回值是：111111111111111";
            }
        }).addFunction(new FunctionWithParamAndResult<String,String>(BlankFragmentA.FUNCTION_NAME) {
            @Override
            public String function(String o) {
                return "参数时："+o+"，返回值是：2222222222222";
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transformation = fragmentManager.beginTransaction();
        BlankFragmentA blankFragmentA = new BlankFragmentA();
        transformation.add(R.id.container,blankFragmentA);
        transformation.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//在主线程中执行
    public void onMessageEventMain(EventBusMessageEntity entity){
        Log.i(TAG, "onMessageEventMain(), current thread is " + Thread.currentThread().getName());
        tv_main.setText("MAIN：current thread is " + Thread.currentThread().getName()+",数据是:"+entity.getMessage());
    }
    @Subscribe(threadMode = ThreadMode.ASYNC)//在另外一个线程中执行
    public void onMessageEventAsync(final EventBusMessageEntity entity){
        final  String ThreadName = Thread.currentThread().getName();
        Log.i(TAG, "onMessageEventAsync(), current thread is " + ThreadName);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MainActivity.this,"current thread is " + Thread.currentThread().getName(),Toast.LENGTH_LONG).show();
                tv_sync.setText("ASYNC：current thread is " + ThreadName+",接收的数据是："+entity.getMessage());
            }
        });

    }
    @Subscribe(threadMode = ThreadMode.POSTING)//在订阅线程中执行
    public void onMessageEventPosting(final  EventBusMessageEntity entity){
        final  String ThreadName = Thread.currentThread().getName();
        Log.i(TAG, "onMessageEventPosting(), current thread is " + ThreadName);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MainActivity.this,"current thread is " + Thread.currentThread().getName(),Toast.LENGTH_LONG).show();
                tv_posting.setText("POSTING：current thread is " + ThreadName+",接收的数据是："+entity.getMessage());
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)//在子线程中执行
    public void onMessageEventBackground(final EventBusMessageEntity entity){
        final  String ThreadName = Thread.currentThread().getName();
        Log.i(TAG, "onMessageEventBackground(), current thread is " + ThreadName);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MainActivity.this,"current thread is " + Thread.currentThread().getName(),Toast.LENGTH_LONG).show();
                tv_background.setText("BACKGROUND：current thread is " + ThreadName+",接收的数据是："+entity.getMessage());
            }
        });
    }
}
