package com.kuliner_di_bekasi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Insert extends Activity {
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	
	 public void onCreate(Bundle savedInstanceState){
	        super.onCreate(savedInstanceState);
	        this.setFullScreen();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.insert);
            Button simpan = (Button) findViewById(R.id.submit);
	        simpan.setOnClickListener(new View.OnClickListener()
	        {
	               public void onClick(View view)
	                 {
	      
	            	   	String result = null;
	            	   	InputStream is = null;
	            	   	EditText editText = (EditText)findViewById(R.id.no);
	            	   	String v1 = editText.getText().toString();
	            	   	EditText editText1 = (EditText)findViewById(R.id.username);
	            	   	String v2 = editText1.getText().toString(); 
	            	   	EditText editText2 = (EditText)findViewById(R.id.judul);
	            	   	String v3 = editText2.getText().toString();
	            	   
	            	   	EditText editText3 = (EditText)findViewById(R.id.isi);
	            	   	String v4 = editText3.getText().toString();
	            	   	
	            	   	EditText editText4 = (EditText)findViewById(R.id.gambar);
	            	   	String v5 = editText4.getText().toString();
	            	   	
	            	   	EditText editText5 = (EditText)findViewById(R.id.alamat);
	            	   	String v6 = editText5.getText().toString();
	            	   	EditText editText6 = (EditText)findViewById(R.id.telepon);
	            	   	String v7 = editText6.getText().toString(); 
	            	   	
	            	   	EditText editText7 = (EditText)findViewById(R.id.web);
	            	   	String v8 = editText7.getText().toString();
	            	   	EditText editText8 = (EditText)findViewById(R.id.waktu);
	            	   	String v9 = editText8.getText().toString();
	            	   	EditText editText9 = (EditText)findViewById(R.id.harga);
	            	   	String v10 = editText9.getText().toString(); 
	            	   	
	            	   	EditText editText10 = (EditText)findViewById(R.id.menu);
	            	   	String v11 = editText10.getText().toString();
	            	   	
	            	   	EditText editText11 = (EditText)findViewById(R.id.gambar1);
	            	   	String v12 = editText11.getText().toString();
	            	   	EditText editText12 = (EditText)findViewById(R.id.gambar2);
	            	   	String v13 = editText12.getText().toString();
	            	   	EditText editText13 = (EditText)findViewById(R.id.gambar3);
	            	   	String v14 = editText13.getText().toString();
	            	   	EditText editText14 = (EditText)findViewById(R.id.gambar4);
	            	   	String v15 = editText14.getText().toString();
	            	   	
	            		EditText editText15 = (EditText)findViewById(R.id.lat);
	            	   	String v16 = editText15.getText().toString();
	            	   	EditText editText16 = (EditText)findViewById(R.id.lng);
	            	   	String v17 = editText16.getText().toString();
	            	   	EditText editText17 = (EditText)findViewById(R.id.type);
	            	   	String v18 = editText17.getText().toString();
	            	   	
	            	   	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	            	   	nameValuePairs.add(new BasicNameValuePair("id_berita",v1));
	            	   	nameValuePairs.add(new BasicNameValuePair("judul",v3));
	            	   	nameValuePairs.add(new BasicNameValuePair("username",v2));
	            	   	
	            	   	nameValuePairs.add(new BasicNameValuePair("isi_berita",v4));
	            	   	nameValuePairs.add(new BasicNameValuePair("alamat",v6));
	            	   	nameValuePairs.add(new BasicNameValuePair("telepon",v7));
	            	   	
	            	   	nameValuePairs.add(new BasicNameValuePair("web",v8));
	            	   	nameValuePairs.add(new BasicNameValuePair("waktu",v9));
	            	   	nameValuePairs.add(new BasicNameValuePair("harga",v10));
	            	   	
	            	   	nameValuePairs.add(new BasicNameValuePair("menu",v11));
	            	   	nameValuePairs.add(new BasicNameValuePair("gambar",v5));
	            	   	nameValuePairs.add(new BasicNameValuePair("menua",v12));
	            	   	
	            	   	nameValuePairs.add(new BasicNameValuePair("menub",v13));
	            	   	nameValuePairs.add(new BasicNameValuePair("menuc",v14));
	            	   	nameValuePairs.add(new BasicNameValuePair("menud",v15));
	            	   	
	            	   	nameValuePairs.add(new BasicNameValuePair("lat",v16));
	            	   	nameValuePairs.add(new BasicNameValuePair("lng",v17));
	            	   	
	            	   	nameValuePairs.add(new BasicNameValuePair("type",v18));

	            	   	StrictMode.setThreadPolicy(policy); 


	        	//http post
	        	try{
	        	        HttpClient httpclient = new DefaultHttpClient();
	        	        HttpPost httppost = new HttpPost("http://sutiakuliner.besaba.com/kuliner/tambah/insert.php");
	        	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        	        HttpResponse response = httpclient.execute(httppost); 
	        	        HttpEntity entity = response.getEntity();
	        	        is = entity.getContent();

	        	        Log.e("log_tag", "connection success ");
	        	        Toast.makeText(getApplicationContext(), "berhasil", Toast.LENGTH_SHORT).show();
	        	   }
	        	
	        	
	        	catch(Exception e)
	        	{
	        	        Log.e("log_tag", "Error in http connection "+e.toString());
	        	        Toast.makeText(getApplicationContext(), "koneksi gagal", Toast.LENGTH_SHORT).show();

	        	}
	        	
	        	try{
	        	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        	        StringBuilder sb = new StringBuilder();
	        	        String line = null;
	        	        while ((line = reader.readLine()) != null) 
	        	        {
	        	                sb.append(line + "\n");
	        	       	        Intent i = new Intent(getBaseContext(),StartActivity.class);
	        	                startActivity(i);
	        	        }
	        	        is.close();

	        	        result=sb.toString();
	        	}
	        	catch(Exception e)
	        	{
	        	       Log.e("log_tag", "Error converting result "+e.toString());
	           	}

	   
	        	try {
	        					JSONObject json_data = new JSONObject(result);
	        	                CharSequence w= (CharSequence) json_data.get("re");
	        	                Toast.makeText(getApplicationContext(), w, Toast.LENGTH_SHORT).show();
	        	     }
	        	catch(JSONException e){
	        	        Log.e("log_tag", "Error parsing data "+e.toString());
	        	        Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
	        	    }
	       }
	       });
	 }
	 
	 private void setFullScreen() {
			requestWindowFeature(Window.FEATURE_NO_TITLE);		
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
								 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
}