package com.kuliner_di_bekasi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_start);
		
		Button user = (Button) findViewById(R.id.userButton);
		Button admin = (Button) findViewById(R.id.adminButton);

		user.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), MenuUtama.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_top,
						R.anim.slide_out_top);
			}
		});
		
		admin.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Login.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_top,
						R.anim.slide_out_top);
			}
		});
	}	
		private void setFullScreen() {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
								 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		
		public void onBackPressed(){
	    }
		
		public void keluar(View view) {
			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
			alt_bld.setMessage("Anda yakin ingin keluar?")
			.setCancelable(false)
			.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
			})
			.setPositiveButton("YA", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			Intent close = new Intent(Intent.ACTION_MAIN);
			close.addCategory(Intent.CATEGORY_HOME);
			close.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(close);
			finish();
			}
			});
			AlertDialog alert = alt_bld.create();
			alert.setTitle("GISKULINER");
			alert.setIcon(R.drawable.ic);
			alert.show();
		}
}
