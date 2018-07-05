package com.kuliner_di_bekasi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{
	
	private EditText user, pass;
	private Button mSubmit;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();  
    private static final String LOGIN_URL = "http://sutiakuliner.besaba.com/login/login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setContentView(R.layout.login);
		
		user = (EditText)findViewById(R.id.username);
		pass = (EditText)findViewById(R.id.password);
		
		mSubmit = (Button)findViewById(R.id.login);
		mSubmit.setOnClickListener(this);
		
		View back = findViewById(R.id.back);
		back.setOnClickListener (this);
		
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.login:
			new AttemptLogin().execute();
		break;
		default:
		break;
		case R.id.back:
			Login.this.finish();
		}
	}
	
	class AttemptLogin extends AsyncTask<String, String, String> {

		boolean failure = false;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Mohon Tunggu");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		
		@Override
		protected String doInBackground(String... args) {
			
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
 
                Log.d("request!", "starting");
               
                JSONObject json = jsonParser.makeHttpRequest(
                       LOGIN_URL, "POST", params);
 
                Log.d("Login attempt", json.toString());
 
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("Login Berhasil!", json.toString());
                	Intent i = new Intent(Login.this, MenuPilihan.class);
                	finish();
    				startActivity(i);
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("Login Gagal!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                	
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}
		
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
		 

}
