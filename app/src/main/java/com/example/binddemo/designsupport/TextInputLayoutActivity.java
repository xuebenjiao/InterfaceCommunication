package com.example.binddemo.designsupport;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binddemo.BaseActivity;
import com.example.binddemo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class TextInputLayoutActivity extends BaseActivity {
    public String[] title;
    public ArrayList<TextView> mViewList;
    @BindView(R.id.veiwpager)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.text_input_layout_id)
    LinearLayout mLayout;
    @BindView(R.id.btn_float_action)
    FloatingActionButton actionButton;
    @BindView(R.id.text_input_layout)
    TextInputLayout textInputLayout;
    @BindView(R.id.text_input_layout1)
    TextInputLayout textInputLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
    }

    private void setView() {
        textInputLayout.getEditText();
        textInputLayout.setHint("请输入用户名");
        textInputLayout.setError("用户名或密码错误");
        textInputLayout.setErrorEnabled(true);//为true时，显示错误信息，反之不显示

        textInputLayout1.getEditText();
        textInputLayout1.setHint("请输入密码");
        textInputLayout1.setError("用户名或密码错误");
        textInputLayout1.setErrorEnabled(true);//为true时，显示错误信息，反之不显示

/*FloatActionButton*/
        actionButton.setRippleColor(Color.GRAY);//设置按下去的波纹颜色
        actionButton.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));//背景色


        mTabLayout.addTab(mTabLayout.newTab().setText("第一页"),true);
        mTabLayout.addTab(mTabLayout.newTab().setText("第二页"),false);
        mTabLayout.addTab(mTabLayout.newTab().setText("第三页"),false);
        mTabLayout.setTabTextColors(Color.BLACK,Color.RED);

        title = new String[]{"显示第一页","显示第二页","显示第三页"};
        mViewList = new ArrayList<>();
        for(int i=0;i<3;i++){
            TextView tv = new TextView(TextInputLayoutActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setText(title[i]);
            tv.setLayoutParams(params);
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextSize(20);
            mViewList.add(tv);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(mViewList,title);
        mViewPager.setAdapter(adapter);
        //用来设置tab的，同时也要覆写  PagerAdapter 的 CharSequence getPageTitle(int position) 方法，要不然 Tab 没有 title
        mTabLayout.setupWithViewPager(mViewPager);
        //关联 TabLayout viewpager
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_text_input_layout;
    }
    @OnClick(R.id.btn_float_action)
    public void setActionButton(View view ){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"你好啊",Snackbar.LENGTH_LONG).setAction("delete", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(TextInputLayoutActivity.this, "delete", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }
    class MyPagerAdapter extends PagerAdapter{
        private ArrayList<View> mList;
        private String[] mTitle;
        MyPagerAdapter(ArrayList list,String[] title){
            this.mList = list;
            mTitle = title;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position),0);
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }
}
