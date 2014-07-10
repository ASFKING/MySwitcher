package com.mingyuanchen.togglebutton.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mingyuanchen.togglebutton.interf.ToggleStateChangeListener;

public class ToggleView extends View{

	private boolean toggleSwitchState = false;//开关打开状态
	private Bitmap switchBackgroudBitmap;//开关背景图片
	private Bitmap slideButtonBackgroundBitmap;//开关滑动块
	private int startX;//触摸时间起始位置
	private boolean isTouching = false;
	private ToggleStateChangeListener listener ;//开关状态改变监听器

	/**
	 * 根据xml配置信息设置背景图片，滑动块图片及开关默认状态
	 * @param context
	 * @param attrs
	 */
	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		String namespace = "http://schemas.android.com/apk/res/com.mingyuanchen.togglebutton";
		//通过xml获取开关状态
		toggleSwitchState = attrs.getAttributeBooleanValue(namespace, "toggleSwitchState", false);
		
		//获取背景图片
		int switchBackgroudResouceId = attrs.getAttributeResourceValue(namespace, "switchBackgroudResouceId", -1);
		setSwitchBackgroundId(switchBackgroudResouceId);
		
		//获取滑动块
		int slideButtonBackgroudResouceId = attrs.getAttributeResourceValue(namespace, "slideButtonBackgroudResouceId", -1);
		setSlidingButtonBackgroundId(slideButtonBackgroudResouceId);
	}

	public ToggleView(Context context) {
		super(context);
	}

	/**
	 * 设置开关状态
	 * @param b
	 */
	public void setSwitchToggleState(boolean b) {
		toggleSwitchState  = b;
		
	}

	/**
	 * 设置开关的背景图片
	 * @param switchBackground
	 */
	public void setSwitchBackgroundId(int switchBackground) {
		switchBackgroudBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);
	}

	/**
	 * 设置滑动开关的背景图片
	 * @param slideButtonBackground
	 */
	public void setSlidingButtonBackgroundId(int slideButtonBackground) {
		slideButtonBackgroundBitmap = BitmapFactory.decodeResource(getResources(), slideButtonBackground);		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//通过查看源代码发现需要设置setMeasuredDimension
		setMeasuredDimension(switchBackgroudBitmap.getWidth(), switchBackgroudBitmap.getHeight());
	}
	
	/**
	 * Android下View控件的绘制流程：measure  ->  layout     ->    draw
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		//绘制开关背景
		canvas.drawBitmap(switchBackgroudBitmap, 0, 0, null);
		//绘制bitmap需要图片在父控件左上角的坐标
		int left = switchBackgroudBitmap.getWidth() - slideButtonBackgroundBitmap.getWidth();
		//绘制开关按钮
		if (isTouching) {
			//动态更改滑动块的位置
			//让手指触摸到滑块的中心，而不是左边
			System.out.println("startX :" +startX);
			int slidingLeft = startX - slideButtonBackgroundBitmap.getWidth() / 2;
			
			if (slidingLeft < 0) {
				slidingLeft = 0;
			}else if(slidingLeft > left){
				slidingLeft = left;
			}

			canvas.drawBitmap(slideButtonBackgroundBitmap, slidingLeft, 0, null);	
//			System.out.println("slidingLeft :" + slidingLeft);
		}else {
			if (toggleSwitchState) {
				//居右显示，left的值为背景宽度减去滑动块的宽度
				canvas.drawBitmap(slideButtonBackgroundBitmap, left, 0, null);			
			}else {
				canvas.drawBitmap(slideButtonBackgroundBitmap, 0, 0, null);			
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = (int) event.getX();
				isTouching = true;
				break;
			case MotionEvent.ACTION_MOVE:
				startX = (int) event.getX();
				break;
			case MotionEvent.ACTION_UP:
				isTouching = false;
				//判断滑动后的状态,如果滑块中心大于背景图片宽度的一半，即为打开状态				
				//判断状态是否改变
				boolean state = (startX > switchBackgroudBitmap.getWidth()/2);
				if (state!=toggleSwitchState && listener!=null) {
					listener.onToggleStateChange(state);
				}
				toggleSwitchState = state;
				break;
	
			default:
				break;
		}
		//重绘
		invalidate();
		return true;
	}
	
	public void setOnToggleStateChangeListener(ToggleStateChangeListener l){
		listener = l;
	}
}
