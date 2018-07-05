package com.kuliner_di_bekasi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);
		
		View mulai = findViewById(R.id.mulai);
		mulai.setOnClickListener (this);
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public void onClick(View w) {
	switch (w.getId()) {
		case R.id.mulai:
			Intent mulai = new Intent(MainActivity.this, StartActivity.class);
			startActivity(mulai);
			break;
		}
	}	
}
