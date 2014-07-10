package com.mingyuanchen.togglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.mingyuanchen.togglebutton.interf.ToggleStateChangeListener;
import com.mingyuanchen.togglebutton.view.ToggleView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}

	private void init() {
		ToggleView toggleView = (ToggleView) findViewById(R.id.toggleview);
//		
//		//设置开关状态
//		toggleView.setSwitchToggleState(true);
//		
//		//设置开关的背景图片
//		toggleView.setSwitchBackgroundId(R.drawable.switch_background);
//		
//		//设置滑动按钮的背景图片
//		toggleView.setSlidingButtonBackgroundId(R.drawable.slide_button_background);
		
		toggleView.setOnToggleStateChangeListener(new ToggleStateChangeListener() {
			
			@Override
			public void onToggleStateChange(boolean toggleState) {
				if (toggleState) {
					Toast.makeText(MainActivity.this, "开关打开", 0).show();
				}else {
					Toast.makeText(MainActivity.this, "开关关闭", 0).show();
				}
			}
		});
	}



}
