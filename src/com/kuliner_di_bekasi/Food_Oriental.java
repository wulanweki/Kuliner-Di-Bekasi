package com.kuliner_di_bekasi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Food_Oriental extends Activity implements OnClickListener{

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	ArrayList<HashMap<String, String>> 
	DaftarKuliner = new ArrayList<HashMap<String, String>>();

	private static String url_berita = "http://sutiakuliner.besaba.com/kuliner/lokoandro/oriental.php";

	public static final String TAG_ID = "id";
	public static final String TAG_JUDUL = "judul";
	public static final String TAG_GAMBAR = "gambar";
	public static final String TAG_TYPE = "type";

	JSONArray string_json = null;
	ListView list;
	LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setContentView(R.layout.list_kuliner);

		DaftarKuliner = new ArrayList<HashMap<String, String>>();
		new AmbilDataKuliner().execute();
		
		View refresh = findViewById(R.id.refresh);
		refresh.setOnClickListener (this);
		
		View back = findViewById(R.id.back);
		back.setOnClickListener (this);
		
		list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	                HashMap<String, String> map = DaftarKuliner.get(position);
	                Intent in = new Intent(getApplicationContext(), IsiKuliner.class);
	                in.putExtra(TAG_ID, map.get(TAG_ID));
	                in.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));
	                in.putExtra(TAG_TYPE, map.get(TAG_TYPE));
	                startActivity(in); 
				}
			});
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public void SetListViewAdapter(ArrayList<HashMap<String, String>> berita) {
		adapter = new LazyAdapter(this, berita);
		list.setAdapter(adapter);
	}

	@SuppressLint("SimpleDateFormat")
	class AmbilDataKuliner extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Food_Oriental.this);
			pDialog.setMessage("Mohon tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url_berita, "GET",params);
			try {
					string_json = json.getJSONArray("berita");
					for (int i = 0; i < string_json.length(); i++) {
						JSONObject c = string_json.getJSONObject(i);

						String id_berita = c.getString(TAG_ID);
						String judul = c.getString(TAG_JUDUL);
						String type = c.getString(TAG_TYPE);
						String link_image = c.getString(TAG_GAMBAR);

						HashMap<String, String> map = new HashMap<String, String>();

						map.put(TAG_ID, id_berita);
						map.put(TAG_JUDUL, judul);
						map.put(TAG_TYPE, type);
						map.put(TAG_GAMBAR, link_image);

						DaftarKuliner.add(map);
					}

			} catch (JSONException e) {
				e.printStackTrace();
			} return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					SetListViewAdapter(DaftarKuliner);
					Calendar c = Calendar.getInstance();
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					String formattedDate = df.format(c.getTime());	
			        TextView updateTime = (TextView) findViewById(R.id.update);
			        updateTime.setText("Terakhir di Update : " + formattedDate);
				}
			});
		}
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.back:
			Food_Oriental.this.finish();
		break;
		case R.id.refresh:
        	finish();
        	startActivity(getIntent());
            Toast.makeText(Food_Oriental.this, "Update Informasi Kuliner", Toast.LENGTH_SHORT).show();            
		break;
		}
	}    	
	
}
