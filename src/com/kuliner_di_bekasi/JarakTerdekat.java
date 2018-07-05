package com.kuliner_di_bekasi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

@SuppressLint("ShowToast")
public class JarakTerdekat extends Activity {
	GoogleMap googleMap;
	ArrayList<LatLng> markerPoints;
	TextView tvDistanceDuration;
	double currentLatitude = 0;
	double currentLongitude = 0;
	private String jsonResult;
	private String url = "http://sutiakuliner.besaba.com/kuliner/lokoandro/berita.php";
	private ListView listView;
	GPSTracker gps;
	
	ArrayList<Double> latList = new ArrayList<Double>();
	ArrayList<Double> longList = new ArrayList<Double>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.jarak);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			currentLatitude = extras.getDouble("paramLat");
			currentLongitude = extras.getDouble("paramLong");
		} else {
			Toast.makeText(getApplicationContext(), "tidak ada data", 1000).show();
		}
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setClickable(true);
		accessWebService();
	}
	
	private class JsonReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
			}
			catch (IOException e) {
			} catch (Exception e) {
			} return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}
			  catch (IOException e) {	
			} catch (Exception e) {
			} return answer;
		}

		@Override
		protected void onPostExecute(String result) {
			ListDrwaer();
		}
	}

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		task.execute(new String[] { url });
	}

	public void ListDrwaer() {
		List<Map<String, String>> kulinerList = new ArrayList<Map<String, String>>();
		try {
			latList.clear();
			longList.clear();
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("berita");
			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String targetLat = jsonChildNode.optString("lat").trim();
				String targetLong = jsonChildNode.optString("lng").trim();
				
				double targetLats = Double.parseDouble(targetLat);
				double targetLongs = Double.parseDouble(targetLong);
				Location mylocation = new Location("");
				Location dest_location = new Location("");
				mylocation.setLatitude(currentLatitude);
				mylocation.setLongitude(currentLongitude);
				dest_location.setLatitude(targetLats);
				dest_location.setLongitude(targetLongs);
				int distance = (int) ((mylocation.distanceTo(dest_location)) / 1000);
					String kl = jsonChildNode.optString("judul");
					String jarak = String.valueOf(distance);
					String output = kl + "\n"+"Jarak : " + jarak + " km";
					kulinerList.add(createKuliner("berita", output));
					latList.add(targetLats);
					longList.add(targetLongs);
			}
		} catch (JSONException e) {
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, kulinerList,
				android.R.layout.simple_list_item_1,
				new String[] { "berita" }, new int[] { android.R.id.text1 });
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view,int position, long id) {	
				double fromLatitude = latList.get(position);
				double fromLongitude = longList.get(position);
				double toLatitude = 0;
				double toLongitude = 0;
				gps = new GPSTracker(JarakTerdekat.this);
				if (gps.canGetLocation()) {
					toLatitude = gps.getLatitude();
					toLongitude = gps.getLongitude();
					Intent in2 = new Intent(getApplicationContext(),Tempat.class);
							 in2.putExtra("firstLat", toLatitude );
							 in2.putExtra( "firstLong", toLongitude );
							 in2.putExtra( "lastLat", fromLatitude );
							 in2.putExtra( "lastLong", fromLongitude );
							 startActivity(in2);
				} else {
					gps.showSettingsAlert();
				}
			}
		});
	}

	private HashMap<String, String> createKuliner(String name, String number) {
		HashMap<String, String> kulinerNameNo = new HashMap<String, String>();
		kulinerNameNo.put(name, number);
		return kulinerNameNo;
	}
}
