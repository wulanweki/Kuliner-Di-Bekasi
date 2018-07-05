package com.kuliner_di_bekasi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.provider.MediaStore;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressLint("ShowToast")
public class Upload extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	Button upload,add;
	TextView status;
	EditText nama;
	
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;
	
	Bitmap bm;

	static String pathToOurFile = "",format;
	String urlServer = "http://sutiakuliner.besaba.com/uploaded_image.php";
	String lineEnd = "\r\n",twoHyphens = "--",boundary =  "*****";
	
	private static final int SELECT_PICTURE = 0;

	int bytesRead, bytesAvailable, bufferSize;
	byte[] buffer;
	int maxBufferSize = 1*1024*1024;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFullScreen();
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.upload);
        upload=(Button)findViewById(R.id.btnChooseImage);
        upload.setOnClickListener(this);
        add=(Button)findViewById(R.id.btnAddImage);
        add.setOnClickListener(this);
        status=(TextView)findViewById(R.id.txtStatusGambar);
        nama=(EditText)findViewById(R.id.editNama); 
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
		case R.id.btnChooseImage:
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PICTURE);
			break;
		case R.id.btnAddImage:
			try {
				bm = BitmapFactory.decodeFile(pathToOurFile);
				executeMultipartPost();
				} catch (Exception e) {
					Log.e(e.getClass().getName(), e.getMessage());
				}
			break;
		default:
			break;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			status.setText(data.getData().toString());
			String[] projection = { MediaStore.Images.Media.DATA };
			
			Cursor cursor = managedQuery(data.getData(), projection, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String filePath = cursor.getString(column_index);
			cursor.close();
			pathToOurFile=filePath;
			format = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		}
	}	
	public void executeMultipartPost() throws Exception {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.JPEG, 75, bos);
			byte[] data = bos.toByteArray();
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(urlServer);
			ByteArrayBody bab = new ByteArrayBody(data, nama.getText().toString()+"."+format);
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
					reqEntity.addPart("uploaded", bab);
					reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
					postRequest.setEntity(reqEntity);
					HttpResponse response = httpClient.execute(postRequest);
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent(), "UTF-8"));
							String sResponse;
							StringBuilder s = new StringBuilder();

							while ((sResponse = reader.readLine()) != null) {
								s = s.append(sResponse);
							}
							Toast.makeText(this, "Penambahan data berhasil", 1).show();
							System.out.println("Response:—————————————————————————————————————————-> " + s);
		} catch (Exception e) {
			Log.e(e.getClass().getName(), e.getMessage());
		}
	}
}