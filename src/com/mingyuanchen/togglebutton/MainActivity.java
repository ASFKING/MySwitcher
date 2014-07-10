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
//		//���ÿ���״̬
//		toggleView.setSwitchToggleState(true);
//		
//		//���ÿ��صı���ͼƬ
//		toggleView.setSwitchBackgroundId(R.drawable.switch_background);
//		
//		//���û�����ť�ı���ͼƬ
//		toggleView.setSlidingButtonBackgroundId(R.drawable.slide_button_background);
		
		toggleView.setOnToggleStateChangeListener(new ToggleStateChangeListener() {
			
			@Override
			public void onToggleStateChange(boolean toggleState) {
				if (toggleState) {
					Toast.makeText(MainActivity.this, "���ش�", 0).show();
				}else {
					Toast.makeText(MainActivity.this, "���عر�", 0).show();
				}
			}
		});
	}



}
