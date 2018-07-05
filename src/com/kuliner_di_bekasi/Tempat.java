package com.kuliner_di_bekasi;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

@SuppressLint("ShowToast")
public class Tempat extends FragmentActivity{
	double currentLatitude = 0;
	double currentLongitude = 0;
	double lastLatitude = 0;
	double lastLongitude = 0;
    LocationManager locManager;
    Drawable drawable;
    Document document;
    Jarak jarak;
    LatLng fromPosition;
    LatLng toPosition;
    GoogleMap mGoogleMap;
    MarkerOptions markerOptions;
    Location location ;
    AlertDialog alertDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.lokasi);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			currentLatitude = extras.getDouble("firstLat");
			currentLongitude = extras.getDouble("firstLong");
			lastLatitude = extras.getDouble("lastLat");
			lastLongitude = extras.getDouble("lastLong");
		} else {
			Toast.makeText(getApplicationContext(), "Tidak ada data", 1000).show();
		}
		
		 jarak = new Jarak();
         SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
         mGoogleMap = supportMapFragment.getMap();

           mGoogleMap.setMyLocationEnabled(true);
          
           mGoogleMap.clear(); 
           LatLng latLng = new LatLng(lastLatitude, lastLongitude);
           mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
           mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
           mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Saya Disini!"));
           mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); 
           
           RadioGroup rgViews = (RadioGroup) findViewById(R.id.rg_views);
   	       rgViews.setOnCheckedChangeListener(new OnCheckedChangeListener() {
   		
   	        @Override
   	        public void onCheckedChanged(RadioGroup group, int checkedId) {
   	            if(checkedId == R.id.rb_normal){
   	            	mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
   	            }else if(checkedId == R.id.rb_satellite){
   	            	mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
   	            }else if(checkedId == R.id.rb_terrain){
   	            	mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
   	            }
   	        }
   	    });
           
           markerOptions = new MarkerOptions();
           fromPosition = new LatLng(currentLatitude, currentLongitude);
           toPosition = new LatLng(lastLatitude, lastLongitude);
           GetRouteTask getRoute = new GetRouteTask();
           getRoute.execute();	
	}	
	
	private class GetRouteTask extends AsyncTask<String, Void, String> {       
        private ProgressDialog Dialog;
        String response = "";
        @Override
        protected void onPreExecute() {
              Dialog = new ProgressDialog(Tempat.this);
              Dialog.setMessage("Loading lokasi...");
              Dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
                    document = jarak.getDocument(fromPosition, toPosition, Jarak.MODE_DRIVING);
                    response = "Berhasil";
        return response;
        }

        @Override
        protected void onPostExecute(String result) {
              mGoogleMap.clear();
              if(response.equalsIgnoreCase("Berhasil")){
              ArrayList<LatLng> directionPoint = jarak.getDirection(document);
              PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.MAGENTA);
              for (int i = 0; i < directionPoint.size(); i++) {
                    rectLine.add(directionPoint.get(i));
              }
              mGoogleMap.addPolyline(rectLine);
              markerOptions.position(toPosition);
              markerOptions.draggable(true);
              mGoogleMap.addMarker(markerOptions);
              } 
              Dialog.dismiss();
        }
  }
	
  @Override
  protected void onStop() {
        super.onStop();
        finish();
  }
}
