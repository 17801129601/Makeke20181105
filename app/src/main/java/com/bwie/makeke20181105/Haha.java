package com.bwie.makeke20181105;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * date:2018/11/5
 * author:别来无恙(别来无恙)
 * function:
 */
public class Haha extends View implements View.OnClickListener {

    //转盘颜色
    private int[] color = new int[]{Color.WHITE,Color.GRAY,Color.YELLOW,Color.BLUE,Color.GREEN,Color.RED};
    //转盘的文字
    private String[] textColor = new String[]{"一等奖","二等奖","三等奖","四等奖","参与奖","谢谢参与"};
    //屏幕的中心点
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Animation mAnimation;
    //初始化一个值
    private boolean rStart = false;

    //首先继承View  实现他的三个方法
    public Haha(Context context) {
        this(context,null);
    }

    public Haha(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public Haha(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取屏幕
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        //获取屏幕的宽和高
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        //获取屏幕的一半  也就是中心点
        mWidth = widthPixels / 2;
        mHeight = heightPixels / 2;
        //初始化Paint;
        initPaint();
        //初始化动画
        initAnimation();
        //点击事件
        setOnClickListener(this);

    }
    private void initPaint() {
        //创建画笔
        mPaint = new Paint();
        //设置颜色
        mPaint.setColor(Color.RED);
        //设置宽度
        mPaint.setStrokeWidth(2);
        //去除毛边
        mPaint.setAntiAlias(true);
        //设置类型
        mPaint.setStyle(Paint.Style.FILL);
    }
    /**
     * 实现他的方法
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //把画布平移到中心点
        canvas.translate(mWidth,mHeight);
        //创建外边的大圆
        RectF rectF = new RectF(-260,-260,260,260);
        //初始化每块的大小
        float start = 60;
        //循环获取颜色
        for (int i = 0; i < 6; i++) {
            //分别设置颜色
            mPaint.setColor(color[i]);
            //画圆弧
            canvas.drawArc(rectF,start*i,60,true,mPaint);
        }
        //设置画笔颜色  画中间的圆
        mPaint.setColor(Color.RED);
        //开始画中间的小圆
        canvas.drawCircle(0,0,100,mPaint);
        //设置画笔颜色  开始写中间的字
        mPaint.setColor(Color.WHITE);
        //设置字体的大小
        mPaint.setTextSize(25);
        //创建Rect
        Rect rect = new Rect();
        //输入要写入的字
        mPaint.getTextBounds("start",0,5,rect);
        //获取rect的宽和高
        int width = rect.width();
        int height = rect.height();
        //把字固定上去
        canvas.drawText("start",-width/2,height/2,mPaint);
        //开始写入文字
        RectF rectF1 = new RectF(-200,-200,200,200);
        //循环获取文字
        for (int i = 0; i < 6; i++) {
            //设置字体的颜色
            mPaint.setColor(Color.BLACK);
            //设置字体的大小
            mPaint.setTextSize(25);
            //创建Path
            Path path = new Path();
            //布局
            path.addArc(rectF1,start*i+20,60);
            //放入圆盘内
            canvas.drawTextOnPath(textColor[i],path,0,0,mPaint);
        }

    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        //旋转动画
        mAnimation = new RotateAnimation(0,360,mWidth,mHeight);
        //设置时间
        mAnimation.setDuration(400);
        //设置重复的次数
        mAnimation.setRepeatCount(-1);
        //设置终止填充
        mAnimation.setFillAfter(true);
        //是xml的一个属性
        mAnimation.setInterpolator(new LinearInterpolator());
        //设置模式
        mAnimation.setRepeatMode(Animation.RESTART);
    }

    @Override
    public void onClick(View v) {
        if (rStart){
            stop();
        }else {
            start();
        }
    }

    private void start() {
        rStart = true;
        //开始动画
        startAnimation(mAnimation);
    }

    private void stop() {
        rStart = false;
        //结束动画
        clearAnimation();
    }
}
