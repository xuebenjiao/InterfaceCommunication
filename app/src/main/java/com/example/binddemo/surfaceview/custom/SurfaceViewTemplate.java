package com.example.binddemo.surfaceview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by xue on 2019/1/31.
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private static final String TAG = SurfaceViewTemplate.class.getName();
    //SurfacecHolder
    private SurfaceHolder mHolder;
    //用于绘图的Canvas
    private Canvas mCanvas;
    //子线程标识
    private boolean mIsDrawing;
    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;

    public SurfaceViewTemplate(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

  /*  public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }*/

    private void initView() {
        mHolder = getHolder();
        //添加回调
        mHolder.addCallback(this);
        //初始化了路径
        mPath = new Path();
        //初始化画笔
        mPaint = new Paint();
        /*Paint.Style.FILL：填充内部
          Paint.Style.FILL_AND_STROKE ：填充内部和描边
          Paint.Style.STROKE ：描边*/
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        //设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
        mPaint.setAntiAlias(true);
        //设置画笔颜色
        mPaint.setColor(Color.RED);
        //设置可以获取焦点  requestFocus()强制获取焦点
        setFocusable(true);
        //强制让焦点获取焦点：
        setFocusableInTouchMode(true);
        //设置屏幕常亮
        this.setKeepScreenOn(true);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsDrawing  =  true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
     long start = System.currentTimeMillis();
     while (mIsDrawing){
         draw();
         long end = System.currentTimeMillis();
         if(end - start <100){
             try{
                 Thread.sleep(100 -end + start);
             }catch (InterruptedException e){
                 e.printStackTrace();
             }
         }
     }
    }

    private void draw() {
        try {
            //锁定画布并返回画布对象
            mCanvas = mHolder.lockCanvas();
            //接下去就是在画布上进行一下draw
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            //当画布内存不为空时，才post,避免出现黑屏的情况。
            if(mCanvas != null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                Log.d(TAG,"onTouchEvent:down");
                mPath.moveTo(x,y);
                break;
            case  MotionEvent.ACTION_MOVE:
                Log.d(TAG,"onTouchEvent:move");
                mPath.lineTo(x,y);
                break;
            case  MotionEvent.ACTION_UP:
                Log.d(TAG,"onTouchEvent:up");
                break;

        }
        return true;
    }
    /**
     * 清屏
     * @return true
     */
    public boolean reDraw(){
        mPath.reset();
        return true;
    }
}
