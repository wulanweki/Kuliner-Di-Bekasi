package com.kuliner_di_bekasi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuPilihan extends Activity implements OnClickListener{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.halaman_admin);
		
		View logout = findViewById(R.id.logout);
		logout.setOnClickListener (this);

		Button tambah = (Button) findViewById(R.id.btnTambah);
		tambah.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Insert.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_in_top);
			}
		});
		
		Button lihat = (Button) findViewById(R.id.btnLihat);
		lihat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Viewss.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_in_top);
			}
		});
		
		Button upload = (Button) findViewById(R.id.btnUpload);
		upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Upload.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_in_top);
			}
		});
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
		public void onClick(View w) {
			switch (w.getId()) {
				case R.id.logout:
					MenuPilihan.this.finish();
			break;
		}
	}
}
