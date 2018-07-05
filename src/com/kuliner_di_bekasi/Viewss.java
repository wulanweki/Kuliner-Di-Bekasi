package com.kuliner_di_bekasi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Viewss extends Activity {

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	ArrayList<HashMap<String, String>> restList;
	private static String url = "http://sutiakuliner.besaba.com/kuliner/tambah/get_all_restaurants.php";	
	private static final String TAG_BERITA = "berita";
	private static final String TAG_ID = "id";
	private static final String TAG_JUDUL = "judul";
	private static final String TAG_SUCCESS = "success";
	protected static final int VISIBLE = 0;

	JSONArray restaurants = null;
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.edit_hasil);

		restList = new ArrayList<HashMap<String, String>>();
		new LoadAllRest().execute();
		lv = (ListView) findViewById(R.id.editListview);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0,android.view.View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView id = (TextView) arg1.findViewById(R.id.noid);
				String rid = id.getText().toString();
				Intent in = new Intent(getApplicationContext(),ManageActivity.class);			
				in.putExtra(TAG_ID, rid);
				startActivity(in);		
			}
		});	
	}
	
	 private void setFullScreen() {
			requestWindowFeature(Window.FEATURE_NO_TITLE);		
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
								 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	class LoadAllRest extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Viewss.this);
			pDialog.setMessage("Mohon Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);
			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					restaurants = json.getJSONArray(TAG_BERITA);
					for (int i = 0; i < restaurants.length(); i++) {
						JSONObject c = restaurants.getJSONObject(i);
						String id = c.getString(TAG_ID);
						String judul = c.getString(TAG_JUDUL);						
						HashMap<String, String> map = new HashMap<String, String>();						
							map.put(TAG_ID, id);
							map.put(TAG_JUDUL, judul);
							restList.add(map);
							}
					}
				
			} catch (JSONException e) {
				e.printStackTrace();
			} return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					ListAdapter adapter = new SimpleAdapter(Viewss.this, restList,R.layout.rest_list_tem, new String[] { TAG_ID,
									TAG_JUDUL }, new int[] { R.id.noid,R.id.namaKuliner });
					lv.setAdapter(adapter);
					TextView tv = (TextView) findViewById(R.id.noKuliner);
					
					if(restList.size()<1){
						tv.setVisibility(Viewss.VISIBLE);
					}
				}
			});
		}
	}
}