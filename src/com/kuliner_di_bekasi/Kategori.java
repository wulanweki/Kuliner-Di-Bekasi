package com.kuliner_di_bekasi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class Kategori extends Activity implements OnClickListener{

		public void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setContentView(R.layout.kategori);
		
		View indo = findViewById(R.id.indonesia);
		indo.setOnClickListener (this);
		
		View barat = findViewById(R.id.barat);
		barat.setOnClickListener (this);
		
		View fast = findViewById(R.id.fastfood);
		fast.setOnClickListener (this);
		
		View oriental = findViewById(R.id.oriental);
		oriental.setOnClickListener (this);
		
		View back = findViewById(R.id.back);
		back.setOnClickListener (this);
		}
		
		private void setFullScreen() {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
								 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		
		public void onClick(View w) {
			switch (w.getId()) {

			case R.id.indonesia:
				Intent indo = new Intent(Kategori.this, Food_Indonesia.class);
				startActivity(indo);
			break;
			case R.id.barat:
				Intent barat = new Intent(Kategori.this, Food_Western.class);
				startActivity(barat);
				break;
			case R.id.oriental:
				Intent oriental = new Intent(Kategori.this,Food_Oriental.class);
				startActivity(oriental);
				break;
			case R.id.fastfood:
				Intent fast = new Intent(Kategori.this,Food_Fast.class);
				startActivity(fast);
				break;
			case R.id.back:
				Kategori.this.finish();
			}
		}
}
