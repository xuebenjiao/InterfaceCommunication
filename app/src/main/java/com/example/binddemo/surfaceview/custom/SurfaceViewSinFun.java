package com.example.binddemo.surfaceview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by xue on 2019/1/31.
 */

public class SurfaceViewSinFun extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder mHolder;
    //绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private  boolean mIsDrawing;
    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;
    private int x = 0,y= 0;

    public SurfaceViewSinFun(Context context) {
        this(context, null);
    }

    public SurfaceViewSinFun(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewSinFun(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaintAndPath();
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }

    private void initPaintAndPath() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPath = new Path();
        //设置路径起始点（0,100）
        mPath.moveTo(0,100);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsDrawing = true;
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
        while (mIsDrawing){
            drawSomeghing();
            x += 1;
            y = (int) (100 * Math.sin(2 * x * Math.PI / 180) + 400);
            //加入新的坐标点
            mPath.lineTo(x,y);
        }

    }

    private void drawSomeghing() {
        try{
            //获取canvas对象
            mCanvas = mHolder.lockCanvas();
            //绘制背景
            mCanvas.drawColor(Color.WHITE);
            //获取路径
            mCanvas.drawPath(mPath,mPaint);



        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(mCanvas != null){
                //释放canvas对象并提交画布
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
