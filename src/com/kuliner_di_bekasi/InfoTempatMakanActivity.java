package com.kuliner_di_bekasi;

import com.google.android.gms.maps.model.LatLng;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InfoTempatMakanActivity extends Activity implements OnClickListener{

	private TextView	tvNama;
	private TextView	tvAlamat;
	private Button		btnGetDirection;

	private LatLng		lokasiTujuan;
	private LatLng		lokasiAwal;
	private String		judul;
	private String		alamat;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_restaurant);
		initialize();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			judul = bundle.getString(Lokasi.KEY_JUDUL);
			alamat = bundle.getString(Lokasi.KEY_ALAMAT);
			lokasiTujuan = new LatLng(bundle.getDouble(Lokasi.KEY_LAT_TUJUAN),
					bundle.getDouble(Lokasi.KEY_LNG_TUJUAN));
			lokasiAwal = new LatLng(bundle.getDouble(Lokasi.KEY_LAT_ASAL),
					bundle.getDouble(Lokasi.KEY_LNG_ASAL));
		}
		setTeksView();
	}

	private void setTeksView()
	{
		tvNama.setText(judul);
		tvAlamat.setText(alamat);
	}

	private void initialize()
	{
		tvAlamat = (TextView) findViewById(R.id.alamatTempatMakan);
		tvNama = (TextView) findViewById(R.id.namaTempatMakan);
		btnGetDirection = (Button) findViewById(R.id.btnArah);
		btnGetDirection.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Bundle bundle = new Bundle();
		bundle.putDouble(Lokasi.KEY_LAT_ASAL, lokasiAwal.latitude);
		bundle.putDouble(Lokasi.KEY_LNG_ASAL, lokasiAwal.longitude);
		bundle.putDouble(Lokasi.KEY_LAT_TUJUAN, lokasiTujuan.latitude);
		bundle.putDouble(Lokasi.KEY_LNG_TUJUAN, lokasiTujuan.longitude);
		bundle.putString(Lokasi.KEY_JUDUL, judul);

		Intent intent = new Intent(this, DirectionActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
