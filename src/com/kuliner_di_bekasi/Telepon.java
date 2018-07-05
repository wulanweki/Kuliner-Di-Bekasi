package com.kuliner_di_bekasi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Telepon extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String telepon = getIntent().getStringExtra("telepon");
		Intent intent_Call = new Intent(Intent.ACTION_CALL);
		intent_Call.setData(Uri.parse("tel:" + telepon));
		startActivity(intent_Call);
	}
}
