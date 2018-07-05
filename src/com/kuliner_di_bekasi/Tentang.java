package com.kuliner_di_bekasi;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;

public class Tentang extends Activity implements OnClickListener{
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	this.setFullScreen();
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.tentang);
	
	View kembali = findViewById(R.id.back);
	kembali.setOnClickListener (this);
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public void onClick(View w) {
	switch (w.getId()) {
	case R.id.back:
		Tentang.this.finish();
	}
	}
}
