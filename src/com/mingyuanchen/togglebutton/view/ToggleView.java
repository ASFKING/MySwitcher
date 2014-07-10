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

	private boolean toggleSwitchState = false;//���ش�״̬
	private Bitmap switchBackgroudBitmap;//���ر���ͼƬ
	private Bitmap slideButtonBackgroundBitmap;//���ػ�����
	private int startX;//����ʱ����ʼλ��
	private boolean isTouching = false;
	private ToggleStateChangeListener listener ;//����״̬�ı������

	/**
	 * ����xml������Ϣ���ñ���ͼƬ��������ͼƬ������Ĭ��״̬
	 * @param context
	 * @param attrs
	 */
	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		String namespace = "http://schemas.android.com/apk/res/com.mingyuanchen.togglebutton";
		//ͨ��xml��ȡ����״̬
		toggleSwitchState = attrs.getAttributeBooleanValue(namespace, "toggleSwitchState", false);
		
		//��ȡ����ͼƬ
		int switchBackgroudResouceId = attrs.getAttributeResourceValue(namespace, "switchBackgroudResouceId", -1);
		setSwitchBackgroundId(switchBackgroudResouceId);
		
		//��ȡ������
		int slideButtonBackgroudResouceId = attrs.getAttributeResourceValue(namespace, "slideButtonBackgroudResouceId", -1);
		setSlidingButtonBackgroundId(slideButtonBackgroudResouceId);
	}

	public ToggleView(Context context) {
		super(context);
	}

	/**
	 * ���ÿ���״̬
	 * @param b
	 */
	public void setSwitchToggleState(boolean b) {
		toggleSwitchState  = b;
		
	}

	/**
	 * ���ÿ��صı���ͼƬ
	 * @param switchBackground
	 */
	public void setSwitchBackgroundId(int switchBackground) {
		switchBackgroudBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);
	}

	/**
	 * ���û������صı���ͼƬ
	 * @param slideButtonBackground
	 */
	public void setSlidingButtonBackgroundId(int slideButtonBackground) {
		slideButtonBackgroundBitmap = BitmapFactory.decodeResource(getResources(), slideButtonBackground);		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//ͨ���鿴Դ���뷢����Ҫ����setMeasuredDimension
		setMeasuredDimension(switchBackgroudBitmap.getWidth(), switchBackgroudBitmap.getHeight());
	}
	
	/**
	 * Android��View�ؼ��Ļ������̣�measure  ->  layout     ->    draw
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		//���ƿ��ر���
		canvas.drawBitmap(switchBackgroudBitmap, 0, 0, null);
		//����bitmap��ҪͼƬ�ڸ��ؼ����Ͻǵ�����
		int left = switchBackgroudBitmap.getWidth() - slideButtonBackgroundBitmap.getWidth();
		//���ƿ��ذ�ť
		if (isTouching) {
			//��̬���Ļ������λ��
			//����ָ��������������ģ����������
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
				//������ʾ��left��ֵΪ������ȼ�ȥ������Ŀ��
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
				//�жϻ������״̬,����������Ĵ��ڱ���ͼƬ��ȵ�һ�룬��Ϊ��״̬				
				//�ж�״̬�Ƿ�ı�
				boolean state = (startX > switchBackgroudBitmap.getWidth()/2);
				if (state!=toggleSwitchState && listener!=null) {
					listener.onToggleStateChange(state);
				}
				toggleSwitchState = state;
				break;
	
			default:
				break;
		}
		//�ػ�
		invalidate();
		return true;
	}
	
	public void setOnToggleStateChangeListener(ToggleStateChangeListener l){
		listener = l;
	}
}
