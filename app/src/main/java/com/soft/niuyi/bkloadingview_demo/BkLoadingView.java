package com.soft.niuyi.bkloadingview_demo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

/**
 * User: niuyi(牛毅)
 * Date: 2017-02-17
 * Time: 15:39
 * Desc: 自定义BK加载中帧动画界面，结合multiple-status_view使用
 */
public class BkLoadingView extends RelativeLayout {

    private AnimationDrawable mLoadingAnimation;//创建帧动画的对象

    private ImageView mLoadingImage;//加载图片

    private TextView mTextView;//加载文字

    public BkLoadingView(Context context) {
        super(context);
        initView(context);
    }

    public BkLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化操作
     */
    private void initView(Context context) {
        //显示loading的图片
        LayoutParams imgLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgLp.addRule(RelativeLayout.CENTER_HORIZONTAL);//设置图片水平居中
        mLoadingImage = new ImageView(context);
        mLoadingImage.setId(Integer.parseInt("1"));//给loadingImg设置一个坐标，可以作为下面空间的参考位置
        mLoadingImage.setLayoutParams(imgLp);
        addView(mLoadingImage);

        //显示提示文字
        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        textLp.addRule(RelativeLayout.BELOW, mLoadingImage.getId());//文字设置在imageview参考位置下面
        mTextView = new TextView(context);
        mTextView.setLayoutParams(textLp);
        addView(mTextView);

        setLoadUi(null);//这里可以配置是否需要加载提示文字
    }

    /**
     * 设置load提示文字
     */
    private void setLoadUi(String loadText) {
        mTextView.setTextColor(Color.BLACK);
        mTextView.setTextSize(14);
        if (loadText == null) {
            showLoadView("");
        } else {
            showLoadView(loadText);
        }
    }

    /**
     * 显示加载动画
     *
     * @param loadText 加载文字，如果不自定义加载文字，此值为空
     */
    public void showLoadView(String loadText) {
        setVisibility(View.VISIBLE);
        //设置提示文字
        mTextView.setText(loadText);
        //给图片设置加载动画
        mLoadingImage.setBackgroundResource(R.drawable.animstion_push);
        mLoadingAnimation = (AnimationDrawable) mLoadingImage.getBackground();
        //启动动画
        mLoadingAnimation.start();
    }

    /**
     * 停止动画
     */
    private void stopLoadingAnimation() {
        setVisibility(View.GONE);
        if (mLoadingAnimation != null && mLoadingAnimation.isRunning()) {
            mLoadingAnimation.stop();
        }
    }

    /**
     * 释放资源
     * 在destroy或者destroy view的时候调用
     */
    public void release() {
        Log.e(TAG, "release: 释放资源");

        stopLoadingAnimation();

        mLoadingAnimation = null;
        if (mLoadingImage != null) {
            mLoadingImage.setImageDrawable(null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }
}
