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
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ManageActivity extends Activity implements OnClickListener{
	
	TextView juduls;
	TextView usernames;
	TextView lats;
	TextView lngs;
	TextView isis;
	TextView alamats;
	TextView telepons;
	TextView webs;
	TextView waktus;
	TextView hargas;
	TextView menus;
	TextView gambar;
	TextView menuas;
	TextView menubs;
	TextView menucs;
	TextView menuds;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_ID = "id";
	private static final String TAG_SUCCESS = "success";

	private static String url_delete_rest = "http://sutiakuliner.besaba.com/kuliner/tambah/hapus.php";
	
	JSONObject json;
 	JSONArray string_json = null; 	
 	String name;
 	String id;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.edit_layout);
		
		id = getIntent().getStringExtra("id");
		
		juduls = (TextView) findViewById(R.id.judul);
		usernames = (TextView) findViewById(R.id.detail);
		isis = (TextView) findViewById(R.id.content);
		alamats = (TextView) findViewById(R.id.alamat);
		telepons = (TextView) findViewById(R.id.telepon);
		webs = (TextView) findViewById(R.id.web);	
		waktus = (TextView) findViewById(R.id.waktu);
		hargas = (TextView) findViewById(R.id.harga);		
		menus = (TextView) findViewById(R.id.menu);
		gambar = (TextView) findViewById(R.id.imageView1);
		menuas = (TextView) findViewById(R.id.imageView2);
		menubs = (TextView) findViewById(R.id.imageView3);
		menucs = (TextView) findViewById(R.id.imageView4);
		menuds = (TextView) findViewById(R.id.imageView5);
		lats = (TextView) findViewById(R.id.lat);
		lngs = (TextView) findViewById(R.id.lng);
		
		View back = findViewById(R.id.back);
		back.setOnClickListener (this);
		
		Button editBtn = (Button) findViewById(R.id.ubah);
		editBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(), Update.class);
				in.putExtra(TAG_ID, id);
				startActivity(in);
				overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_top);	
			}
		});
		
		Button deleteBtn = (Button) findViewById(R.id.hapus);
		deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                new Hapus().execute();
			}
		});
	}
	
	 private void setFullScreen() {
			requestWindowFeature(Window.FEATURE_NO_TITLE);		
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
								 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	
    class Hapus extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManageActivity.this);
            pDialog.setMessage("Hapus Restaurant...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        protected String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("id", id));
                JSONObject json = jsonParser.makeHttpRequest(url_delete_rest, "POST", params);
 
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Intent i = new Intent(getApplicationContext(), Viewss.class);
                    i.putExtra("MESSAGE", "delete");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in,R.anim.slide_in_top);		
                    finish();
                    
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } return null;
        }
 
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }
        
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			ManageActivity.this.finish();
		}
	}
}
