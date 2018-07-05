package com.kuliner_di_bekasi;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

public class Petunjuk extends Activity implements OnClickListener{
	
	private MediaController mn;
	
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	this.setFullScreen();
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.petunjuk);
	
	View kembali = findViewById(R.id.back);
	kembali.setOnClickListener (this);
	
	setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	
	VideoView vd = (VideoView) findViewById(R.id.video);
	
	Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
	
	mn = new MediaController(this);
	vd.setMediaController(mn);
	
	vd.setVideoURI(uri);
	vd.start();
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public void onClick(View w) {
	switch (w.getId()) {
	case R.id.back:
		Petunjuk.this.finish();
	}
	}
}
