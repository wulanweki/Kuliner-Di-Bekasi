package com.kuliner_di_bekasi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Update extends Activity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    private static final String url_rest_details = "http://sutiakuliner.besaba.com/kuliner/tambah/view_id.php";
    private static final String url_update_rest = "http://sutiakuliner.besaba.com/kuliner/tambah/update.php";
    
    private static final String TAG_BERITA = "berita";
	private static final String TAG_ID = "id";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_JUDUL = "judul";
	private static final String TAG_ISI = "isi";
	private static final String TAG_GAMBAR = "gambar";
	private static final String TAG_ALAMAT = "alamat";
	private static final String TAG_TELEPON = "telepon";
	private static final String TAG_WEB = "web";
	private static final String TAG_WAKTU = "waktu";
	private static final String TAG_HARGA = "harga";
	private static final String TAG_MENU = "menu";
	private static final String TAG_MENUA = "menua";
	private static final String TAG_MENUB = "menub";
	private static final String TAG_MENUC = "menuc";
	private static final String TAG_MENUD = "menud";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	private static final String TAG_TYPE = "type";
	private static final String TAG_SUCCESS = "success";
  	
  	 String id;
  	
  	 EditText inputjudul;
     EditText inputusername;
     EditText inputisi;
     EditText inputalamat;
     EditText inputtelepon;
     EditText inputweb;
     EditText inputwaktu;
     EditText inputharga;
     EditText inputmenu;
     EditText inputgambar;
     EditText inputgambar1;
     EditText inputgambar2;
     EditText inputgambar3;
     EditText inputgambar4;
     EditText inputlat;
     EditText inputlng;
     EditText inputtipe;
     
     JSONObject json = null;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.edit);
		
		inputjudul = (EditText) findViewById(R.id.inputjudul);
		inputusername = (EditText) findViewById(R.id.inputusername);
		inputisi = (EditText) findViewById(R.id.inputisi);
		inputalamat = (EditText) findViewById(R.id.inputalamat);
		inputtelepon = (EditText) findViewById(R.id.inputtelepon);
		inputweb = (EditText) findViewById(R.id.inputweb);
		inputwaktu = (EditText) findViewById(R.id.inputwaktu);
		inputharga = (EditText) findViewById(R.id.inputharga);
		inputmenu = (EditText) findViewById(R.id.inputmenu);
		
		inputgambar = (EditText) findViewById(R.id.inputgambar);
		inputgambar1 = (EditText) findViewById(R.id.inputgambar1);
		inputgambar2 = (EditText) findViewById(R.id.inputgambar2);
		inputgambar3 = (EditText) findViewById(R.id.inputgambar3);
		inputgambar4 = (EditText) findViewById(R.id.inputgambar4);
		
		inputlat = (EditText) findViewById(R.id.inputlat);
		inputlng = (EditText) findViewById(R.id.inputlng);
		inputtipe = (EditText) findViewById(R.id.inputtipe);
		
		Button btnUpdate = (Button) findViewById(R.id.edit);
		btnUpdate.setText("Ubah");
        Intent i = getIntent();
        id = i.getStringExtra(TAG_ID);
        new GetRestDetails().execute();
		btnUpdate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(inputjudul.getText().toString().trim().contentEquals("")
						||inputusername.getText().toString().trim().contentEquals("")
						||inputisi.getText().toString().trim().contentEquals("")
						||inputalamat.getText().toString().trim().contentEquals("")
						||inputtelepon.getText().toString().trim().contentEquals("")
						||inputwaktu.getText().toString().trim().contentEquals("")
						||inputharga.getText().toString().trim().contentEquals("")
						||inputmenu.getText().toString().trim().contentEquals("")
						||inputgambar.getText().toString().trim().contentEquals("")
						||inputgambar1.getText().toString().trim().contentEquals("")
						||inputgambar2.getText().toString().trim().contentEquals("")
						||inputgambar3.getText().toString().trim().contentEquals("")
						||inputgambar4.getText().toString().trim().contentEquals("")
						||inputlat.getText().toString().trim().contentEquals("")
						||inputlng.getText().toString().trim().contentEquals("")
						||inputtipe.getText().toString().trim().contentEquals(""))
				{
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(Update.this);
					alertDialog.setTitle("Input validation");
					alertDialog.setMessage("Isi Semua Data");
					alertDialog.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					alertDialog.show();
				}
				else{
	                new SaveRestDetails().execute();	
				}
				
			}
		});
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

    class GetRestDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Update.this);
            pDialog.setMessage("Mohon Tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        protected String doInBackground(String... args) {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("id", id));
                        JSONObject json = jsonParser.makeHttpRequest(url_rest_details, "GET", params);
                        return json.toString();
        }
 
        protected void onPostExecute(final String jsonStr) {
            pDialog.dismiss();
         			runOnUiThread(new Runnable() {
         				@Override
         				public void run() {
         					try {
         						json = new JSONObject(jsonStr);
         					} catch (JSONException e1) {
         						e1.printStackTrace();
         					}

         					try {
         						int success = json.getInt(TAG_SUCCESS);
                                if (success == 1) {
                                    JSONArray restObj = json.getJSONArray(TAG_BERITA);
                                    JSONObject rest = restObj.getJSONObject(0);
                                
                                    inputjudul.setText(rest.getString(TAG_JUDUL));
                                    inputusername.setText(rest.getString(TAG_USERNAME));
                                    inputisi.setText(rest.getString(TAG_ISI));
                                    inputalamat.setText(rest.getString(TAG_ALAMAT));
                                    inputtelepon.setText(rest.getString(TAG_TELEPON));
                                    inputweb.setText(rest.getString(TAG_WEB));
                                    inputwaktu.setText(rest.getString(TAG_WAKTU));
                                    inputharga.setText(rest.getString(TAG_HARGA));
                                    inputmenu.setText(rest.getString(TAG_MENU));
                                    
                                    inputgambar.setText(rest.getString(TAG_GAMBAR));
                                    inputgambar1.setText(rest.getString(TAG_MENUA));
                                    inputgambar2.setText(rest.getString(TAG_MENUB));
                                    inputgambar3.setText(rest.getString(TAG_MENUC));
                                    inputgambar4.setText(rest.getString(TAG_MENUD));
                                    inputlat.setText(rest.getString(TAG_LAT));
                                    inputlng.setText(rest.getString(TAG_LNG));
                                    inputtipe.setText(rest.getString(TAG_TYPE));
                                    
                                }else{
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
         				}
         			});
        }
    }
 
    class SaveRestDetails extends AsyncTask<String, String, String> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Update.this);
            pDialog.setMessage("Simpan Tempat Kuliner...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        protected String doInBackground(String... args) {
        	String judul = inputjudul.getText().toString();
            String username = inputusername.getText().toString();
            String isi = inputisi.getText().toString();
            String alamat = inputalamat.getText().toString();
            String telepon = inputtelepon.getText().toString();
            String web = inputweb.getText().toString();
            String waktu = inputwaktu.getText().toString();
            String harga = inputharga.getText().toString();
            String menu = inputmenu.getText().toString();           
            String gambar = inputgambar.getText().toString();
            String menua = inputgambar1.getText().toString();
            String menub = inputgambar2.getText().toString();
            String menuc = inputgambar3.getText().toString();
            String menud = inputgambar4.getText().toString();           
            String lat = inputlat.getText().toString();
            String lng = inputlng.getText().toString();
            String type = inputtipe.getText().toString();
            
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_berita", id));
            params.add(new BasicNameValuePair("judul", judul));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("isi_berita", isi));
            params.add(new BasicNameValuePair("alamat", alamat));
            params.add(new BasicNameValuePair("telepon", telepon));
            params.add(new BasicNameValuePair("web", web));
            params.add(new BasicNameValuePair("waktu", waktu));
            params.add(new BasicNameValuePair("harga", harga));
            params.add(new BasicNameValuePair("menu", menu));            
            params.add(new BasicNameValuePair("gambar", gambar));
            params.add(new BasicNameValuePair("menua", menua));
            params.add(new BasicNameValuePair("menub", menub));
            params.add(new BasicNameValuePair("menuc", menuc));
            params.add(new BasicNameValuePair("menud", menud));           
            params.add(new BasicNameValuePair("lat", lat));
            params.add(new BasicNameValuePair("lng", lng));
            params.add(new BasicNameValuePair("type", type));
            
            JSONObject json = jsonParser.makeHttpRequest(url_update_rest,"POST", params);
 
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent i = new Intent(getApplicationContext(), Viewss.class);
                    i.putExtra("MESSAGE", "edit");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    overridePendingTransition(R.anim.alpha,
    						R.anim.slide_out_top);
                    startActivity(i);
                    finish();
                } else {        
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } return null;
        }
 
        protected void onPostExecute(String file_url) {
            pDialog.dismiss(); }
    }  
}
