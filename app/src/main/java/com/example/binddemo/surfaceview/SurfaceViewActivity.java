package com.example.binddemo.surfaceview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.binddemo.BaseActivity;
import com.example.binddemo.R;
import com.example.binddemo.surfaceview.custom.SurfaceViewSinFun;
import com.example.binddemo.surfaceview.custom.SurfaceViewTemplate;

import butterknife.BindView;
import butterknife.OnClick;

public class SurfaceViewActivity extends BaseActivity {
/*   @BindView(R.id.mySurfaceView)
  SurfaceViewTemplate surfaceViewTemplate;*/

    @BindView(R.id.image_id)
    ImageView mImageView;
    @BindView(R.id.surfaceview_sin_fun)
    SurfaceViewSinFun surfaceViewSinFun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_surface_view;
    }
    @OnClick(R.id.clear_btn)
    public void clearSurfaceView(View view){
//        surfaceViewTemplate.reDraw();
    }
    @OnClick(R.id.getview_btn)
    public void getBitmapFromSurfaceView(View view){
        mImageView.setImageBitmap(convertViewToBitmap(surfaceViewSinFun));
    }
    public  Bitmap convertViewToBitmap(View view){
        /*view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;*/
        return null;
    }
}
