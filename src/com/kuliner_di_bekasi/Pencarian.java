package com.kuliner_di_bekasi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Pencarian extends Activity {

	private String type;
	private String judul;

	private ProgressDialog pDialog;
	private JSONParser jParser = new JSONParser();
	private static String url = "http://sutiakuliner.besaba.com/kuliner/tambah/search_restaurants.php";

	private static final String TAG_BERITA = "berita";
	private static final String TAG_ID = "id";
	private static final String TAG_JUDUL = "judul";
	private static final String TAG_TYPE = "type";
	private static final String TAG_SUCCESS = "success";

	JSONObject json;

	JSONArray restaurantData = null;
	ArrayList<HashMap<String, String>> restaurantList = new ArrayList<HashMap<String, String>>();
	ListView myList;
	private String[] id_berita;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.pencarian_hasil);

		myList = (ListView) findViewById(R.id.pencarianlistview);
		if (getIntent().getExtras() != null) {
			Bundle extras = getIntent().getExtras();
			judul = extras.getString("REST_NAME");
			type = extras.getString("REST_TYPE");		
		} new LoadRestaurants().execute();
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}


class LoadRestaurants extends AsyncTask<String, String, String> {

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(Pencarian.this);
		pDialog.setMessage("Mohon Tunggu...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... args) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("judul", judul));
		params.add(new BasicNameValuePair("type", type));
		JSONObject json = jParser.makeHttpRequest(url, "GET", params);
		Log.v("SearchRestaurantJSON: ", json.toString());
		return json.toString();
	}

	@Override
	protected void onPostExecute(final String jsonStr) {
		pDialog.dismiss();
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					json = new JSONObject(jsonStr);
				} catch (JSONException e1) {
					e1.printStackTrace();
					error("Tidak ada nama tempat kuliner");
				}

				try {
					int success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						restaurantData = json.getJSONArray(TAG_BERITA);
						displayTechData(restaurantData.toString());
					} else {
						error("Tidak ada nama tempat kuliner yang dicari!");
					}
				} catch (JSONException e) {
					error("Terdapat Error! Coba Kembali...!");
					e.printStackTrace();
				}
			}
		});
	}
}

public void error(String error) {
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setTitle("Error");
	builder.setMessage(error);
	builder.setCancelable(false);
	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int id) {
			Intent i = new Intent(getApplicationContext(),TabsViewPagerFragmentActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_out_top, R.anim.slide_in_top);
			finish();			
		}
	});
	AlertDialog alert = builder.create();
	alert.show();
}

public void displayTechData(String result) {
	JSONArray restaurantData = null;
	
	try {
		restaurantList.clear();
		restaurantData = new JSONArray(result);

		id_berita = new String[restaurantData.length()];
		for (int i = 0; i < restaurantData.length(); i++) {
			JSONObject td = restaurantData.getJSONObject(i);
			String id = td.getString(TAG_ID);
			id_berita[i] = id;
			
			String judul = td.getString(TAG_JUDUL);
			String type = td.getString(TAG_TYPE);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(TAG_ID, id);
			map.put(TAG_JUDUL, judul);
			map.put(TAG_TYPE, type);
			restaurantList.add(map);
		}

	} catch (JSONException e) {
		e.printStackTrace();
		error("Error parsing json");
	}

	ListAdapter adapter = new SimpleAdapter(Pencarian.this,
			restaurantList, R.layout.pencarian_list, new String[] {
					TAG_JUDUL, TAG_TYPE}, new int[] {
					R.id.restaurantName, R.id.type});
	myList.setAdapter(adapter);
	myList.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			Intent i = new Intent(getApplicationContext(), IsiKuliner.class);
			i.putExtra("id", id_berita[arg2]);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
		}
	
	});
}

@SuppressLint("SimpleDateFormat")
protected void onPostExecute(String file_url) {

	pDialog.dismiss();

	runOnUiThread(new Runnable() {
		public void run() {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String formattedDate = df.format(c.getTime());		
	        TextView updateTime = (TextView) findViewById(R.id.update);
	        updateTime.setText("Terakhir di Update : " + formattedDate);

		}
	});
}
}