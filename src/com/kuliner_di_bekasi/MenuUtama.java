package com.kuliner_di_bekasi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class MenuUtama extends Activity implements OnClickListener{
	GPSTracker gps;
	double lat =0;
	double lng;
	
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	this.setFullScreen();
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	setContentView(R.layout.menu_utama);
	
	View kategori = findViewById(R.id.kategori);
	kategori.setOnClickListener (this);
	
	View cari = findViewById(R.id.pencarian);
	cari.setOnClickListener (this);
	
	View peta = findViewById(R.id.peta);
	peta.setOnClickListener (this);
	
	View petunjuk = findViewById(R.id.petunjuk);
	petunjuk.setOnClickListener (this);
	
	View tentang = findViewById(R.id.tentang);
	tentang.setOnClickListener (this);
	
	View lokasiterdekat = findViewById(R.id.lokasiterdekat);
	lokasiterdekat.setOnClickListener (this);
	
	View back = findViewById(R.id.back);
	back.setOnClickListener (this);
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public void onClick(View w) {
		switch (w.getId()) {
			
			case R.id.kategori:
				Intent kategori = new Intent(MenuUtama.this, Kategori.class);
				startActivity(kategori);
			break;
			case R.id.peta:
				Intent peta = new Intent(MenuUtama.this,Lokasi.class);
				startActivity(peta);
				break;
			case R.id.pencarian:
				Intent cari = new Intent(MenuUtama.this,TabsViewPagerFragmentActivity.class);
				startActivity(cari);
				break;
			case R.id.petunjuk:
				Intent petunjuk = new Intent(MenuUtama.this,Petunjuk.class);
				startActivity(petunjuk);
				break;
			case R.id.tentang:
				Intent tentang = new Intent(MenuUtama.this,Tentang.class);
				startActivity(tentang);
				break;
			case R.id.lokasiterdekat:
				double lat = 0;
				double lng = 0;
				
				gps = new GPSTracker(MenuUtama.this);
				if (gps.canGetLocation()) {
				lat = gps.getLatitude();
				lng = gps.getLongitude();
					Intent in2 = new Intent(getApplicationContext(),
							JarakTerdekat.class);
					in2.putExtra("paramLat", lat);
					in2.putExtra("paramLong", lng);
					startActivity(in2);
				} else {
					gps.showSettingsAlert();
				}
				
				break;
			case R.id.back:
				MenuUtama.this.finish();
		}
	}
}
