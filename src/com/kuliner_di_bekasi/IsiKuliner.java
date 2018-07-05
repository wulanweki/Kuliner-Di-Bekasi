package com.kuliner_di_bekasi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class IsiKuliner extends Activity implements OnClickListener{
	
	ImageLoader imageLoader;
	JSONArray string_json = null;
	
	String idberita;
	String judul;
	String alamat;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_ID = "id";
	public static final String TAG_JUDUL = "judul";
	public static final String TAG_ALAMAT = "alamat";
	public static final String TAG_GAMBAR = "gambar";
	public static final String TAG_MENUA = "menua";
	public static final String TAG_MENUB = "menub";
	public static final String TAG_MENUC = "menuc";
	public static final String TAG_MENUD = "menud";
	private static final String TAG_URL = "web";
	private static final String TAG_TELEPON = "telepon";
	
 	String web_menu = null;
 	String telepon_menu = null;
 
 	Button webs;
 	Button telepon;
 	
	private static final String url_detail_berita = "http://sutiakuliner.besaba.com/kuliner/lokoandro/detailberita.php";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setContentView(R.layout.single_list_item);
		
		Intent i = getIntent();
		
		idberita = i.getStringExtra(TAG_ID);
		
		View back = findViewById(R.id.back);
		back.setOnClickListener (this);

		new AmbilDetailBerita().execute();
			
		Button webs = (Button) findViewById(R.id.buttonweb);
		webs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Web.class);
				//pass the url for restaurant
				i.putExtra("web", web_menu);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);				}
		});
		
		Button telepon = (Button) findViewById(R.id.buttontelepon);
		telepon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Telepon.class);
				//pass the url for restaurant
				i.putExtra("telepon", telepon_menu);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);				}
		});
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	

	class AmbilDetailBerita extends AsyncTask<String, String, String> { 
 
		@Override
		protected void onPreExecute() { 
			super.onPreExecute();
			pDialog = new ProgressDialog(IsiKuliner.this);
			pDialog.setMessage("Mohon Tunggu ... !");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... params) {

					try {

						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("idberita",idberita));

						JSONObject json = jsonParser.makeHttpRequest(url_detail_berita, "GET", params1);
						string_json = json.getJSONArray("berita");
						displayDetails(string_json.toString());

							runOnUiThread(new Runnable() {
								public void run() {

									ImageView thumb_image = (ImageView) findViewById(R.id.imageView1);
									TextView judul = (TextView) findViewById(R.id.judul);
							        TextView detail = (TextView) findViewById(R.id.detail);
							        TextView isi = (TextView) findViewById(R.id.content);
							        
							        TextView alamat = (TextView) findViewById(R.id.alamat);
							        TextView telepon = (TextView) findViewById(R.id.telepon);
							        TextView web = (TextView) findViewById(R.id.web);
							        TextView waktu = (TextView) findViewById(R.id.waktu);
							        TextView harga = (TextView) findViewById(R.id.harga);
							        
							        TextView menu = (TextView) findViewById(R.id.menu);
							        ImageView thumb_image2 = (ImageView) findViewById(R.id.imageView2);
							        ImageView thumb_image3 = (ImageView) findViewById(R.id.imageView3);
							        ImageView thumb_image4 = (ImageView) findViewById(R.id.imageView4);
							        ImageView thumb_image5 = (ImageView) findViewById(R.id.imageView5);

							try {
								// ambil objek member pertama dari JSON Array
								JSONObject ar = string_json.getJSONObject(0);
								String judul_d = ar.getString("judul");
								String detail_d = "Diposting Oleh : "+ar.getString("username");
								String isi_d = ar.getString("isi");	
								
								String alamat_d = ar.getString("alamat");
								String telepon_d = ar.getString("telepon");
								String web_d = ar.getString("web");
								String waktu_d = ar.getString("waktu");
								String harga_d = ar.getString("harga");
								
								String menu_d = ar.getString("menu");
								
					        judul.setText(judul_d);
					        detail.setText(detail_d);
					        isi.setText(isi_d);
					        
					        alamat.setText(alamat_d);
					        telepon.setText(telepon_d);
					        web.setText(web_d);
					        
					        waktu.setText(waktu_d);
					        harga.setText(harga_d);
					        menu.setText(menu_d);
					        
					        imageLoader = new ImageLoader(getApplicationContext()); 
					        imageLoader.DisplayImage(ar.getString(TAG_GAMBAR),thumb_image); 
					        
					        imageLoader.DisplayImage(ar.getString(TAG_MENUA),thumb_image2);
					        imageLoader.DisplayImage(ar.getString(TAG_MENUB),thumb_image3);
					        imageLoader.DisplayImage(ar.getString(TAG_MENUC),thumb_image4);
					        imageLoader.DisplayImage(ar.getString(TAG_MENUD),thumb_image5);
		        
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								}
						}
					});
							
					} catch (JSONException e) {
						e.printStackTrace();
				}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}
	}
	
	public void displayDetails(String result) {
		JSONArray string_json = null;
		
		try {

			string_json = new JSONArray(result);
			for (int i = 0; i < string_json.length(); i++) {
				JSONObject rd = string_json.getJSONObject(i);
				
				idberita = rd.getString(TAG_ID);
				
				judul = rd.getString(TAG_JUDUL);
				
				telepon_menu = rd.getString(TAG_TELEPON);
				if(telepon_menu == null){
					telepon.setEnabled(false);
					telepon.setVisibility(View.INVISIBLE);
				}
				
	            web_menu = rd.getString(TAG_URL);
				if(web_menu == null){
					webs.setEnabled(false);
					webs.setVisibility(View.INVISIBLE);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}

	public void onClick(View w) {
		switch (w.getId()) {
			case R.id.back:
				IsiKuliner.this.finish();
		}
	}
}
