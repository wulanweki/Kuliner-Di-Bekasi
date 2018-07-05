package com.kuliner_di_bekasi;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class Web extends Activity {

	WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		
		//get url for menu from previous activity
		String web = getIntent().getStringExtra("web");
		Log.v("WEB", web);
		webView = (WebView) findViewById(R.id.webs);
		webView.canGoBack();
		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setBuiltInZoomControls(true);
		
		webView.getSettings().setUseWideViewPort(true);
		webView.loadUrl(web);
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
	        webView.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}

