package com.kuliner_di_bekasi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;


public class PencarianTab extends Fragment {
	
	private ProgressDialog pDialog;
	private ProgressDialog pDialog2;
	JSONParser jParser = new JSONParser();

	private static String url = "http://sutiakuliner.besaba.com/kuliner/tambah/get_all_restaurants.php";
	private static String url_types = "http://sutiakuliner.besaba.com/kuliner/tambah/get_all_types.php";
	
	Activity a;
	
	private JSONObject json;

	JSONArray kulinerData = null;
	JSONArray kulinerTypeData = null;
	
	private static final String TAG_BERITA = "berita";
	private static final String TAG_ID = "id";
	private static final String TAG_JUDUL = "judul";
	private static final String TAG_TYPE = "type";
	private static final String TAG_SUCCESS = "success";
	
	String[] rname;
	String[] rid;
	String[] type;
	
	Spinner spinner;
	AutoCompleteTextView A;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
            return null;
        }
		return (LinearLayout)inflater.inflate(R.layout.pencarian_layout, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		a = getActivity();
		
		spinner = (Spinner) getActivity().findViewById(R.id.cariSpinner);
		A = (AutoCompleteTextView) getActivity().findViewById(R.id.searchAutoComplete);
		
		new LoadNamaKuliner().execute();		
		new LoadTipe().execute();
		
		Button searchBtn = (Button) a.findViewById(R.id.buttonCari);
		searchBtn.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO get user input
				if(A.getText().toString().trim().isEmpty()){
					error("Tolong Masukan Nama Tempat Kuliner",false);
				}
				else{
				String judul = A.getText().toString();
				String type = spinner.getSelectedItem().toString();
				
				Bundle bundle = new Bundle();
				bundle.putString("REST_NAME", judul);
				bundle.putString("REST_TYPE", type);
                
				Intent i = new Intent(a, Pencarian.class);
				i.putExtras(bundle);
				startActivity(i);
				a.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
				}
			}
		});
	}
	
	class LoadNamaKuliner extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(a);
			pDialog.setMessage("Loading Tempat Kuliner. Mohon Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);
			return json.toString();
		}

		@Override
		protected void onPostExecute(final String jsonStr) {
			pDialog.dismiss();
			a.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						json = new JSONObject(jsonStr);
					} catch (JSONException e1) {
						e1.printStackTrace();
						error("Tidak ada daftar Nama Tempat Kuliner",true);
					}

					try {
						int success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							kulinerData = json.getJSONArray(TAG_BERITA);
							displayTechData(kulinerData.toString());
						} else {
							error("Tidak ada daftar Nama Tempat Kuliner!",true);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	class LoadTipe extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog2 = new ProgressDialog(a);
			pDialog2.setMessage("Loading Tempat Kuliner. Mohon tunggu...!");
			pDialog2.setIndeterminate(false);
			pDialog2.setCancelable(true);
			pDialog2.show();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url_types, "GET", params);
			return json.toString();
		}
		
		@Override
		protected void onPostExecute(final String jsonStr) {
			pDialog2.dismiss();

			a.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						json = new JSONObject(jsonStr);
					} catch (JSONException e1) {
						e1.printStackTrace();
						error("Tidak Ada Tipe Tempat Kuliner",true);
					}

					try {
						int success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							kulinerTypeData = json.getJSONArray(TAG_BERITA);
							displayTypes(kulinerTypeData.toString());
						} else {
							error("Tidak Ada Tipe Tempat Kuliner!",true);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
	}
	
	public void error(String error, boolean flag) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setTitle("Error");
		builder.setMessage(error);
		builder.setCancelable(false);
		if(flag==true){
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Intent i = new Intent(a.getApplicationContext(),TabsViewPagerFragmentActivity.class);			
				startActivity(i);
				a.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
				a.finish();			
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		}
		else{
			builder.setPositiveButton("OK", null);
		AlertDialog alert = builder.create();
		alert.show();
		
		}
	}

	public void displayTechData(String result) {
		JSONArray restaurantData = null;
		
		try {
			
			restaurantData = new JSONArray(result);

			rname = new String[restaurantData.length()];
			rid = new String[restaurantData.length()];
			for (int i = 0; i < restaurantData.length(); i++) {
				JSONObject td = restaurantData.getJSONObject(i);
				String id = td.getString(TAG_ID);
				rid[i] = id;				
				String judul = td.getString(TAG_JUDUL);
				rname[i] = judul;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
	                R.layout.pencarian_spinner, rname);
		    
		    A = (AutoCompleteTextView) getActivity().findViewById(R.id.searchAutoComplete);
		    A.setAdapter(adapter);
	}
	
	public void displayTypes(String result) {
		JSONArray restaurantData = null;
		
		try {
			
			restaurantData = new JSONArray(result);
			type = new String[kulinerTypeData.length()+1];
			type[0] = "All";
			int jsonCount = 0;
			for(int i=1;i<kulinerTypeData.length()+1;i++){
				
				JSONObject td = restaurantData.getJSONObject(jsonCount);
				String types = td.getString(TAG_TYPE);
				type[i] = types;
				jsonCount++;
			}
					
		} catch (JSONException e) {
			e.printStackTrace();
		}

	    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
	            getActivity().getApplicationContext(), R.layout.pencarian_spinner, type);
	   spinner.setAdapter(spinnerArrayAdapter);

	}
	
}