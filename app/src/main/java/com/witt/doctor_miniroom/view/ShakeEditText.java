package com.witt.doctor_miniroom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

/**
 * 自定义编辑框
 *
 *
 * @author witt
 *
 */
@SuppressLint("AppCompatCustomView")
public class ShakeEditText extends EditText {

	public ShakeEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ShakeEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ShakeEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 设置晃动动画
	 */
	public void setShakeAnimation() {
		this.setAnimation(shakeAnimation(5));
	}

	/**
	 * 开始晃动动画
	 */
	public void startAnimation() {
		setShakeAnimation();
		this.startAnimation(shakeAnimation(5));
	}

	/**
	 * 晃动动画
	 * 
	 * @param counts
	 *            1秒钟晃动多少下
	 * @return
	 */
	public static Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		// 设置一个循环加速器，使用传入的次数就会出现摆动的效果。
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(500);
		return translateAnimation;
	}
}
