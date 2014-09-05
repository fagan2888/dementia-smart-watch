package com.team7.smartwatch;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Locator {

	private Context context;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private Location lastLocation;
	private Date lastTime;
	
	private static final String TAG = Locator.class.getName();
	private static final String POST_URL = "http://192.168.1.20:8080/updatelocation";

	/** Locator provides access to the device's last known location and the
	 *  time at which the location was last updated. */
	public Locator(Context context) {
		this.context = context;
		locationListener = new MyLocationListener();
		locationManager = (LocationManager) this.context
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				5000, 10, locationListener);
	}
	
	public Location getLastLocation() {
		return lastLocation;
	}

	public Date getLastTime() {
		return lastTime;
	}

	private class MyLocationListener implements LocationListener {
		
		@Override
		public void onLocationChanged(Location loc) {
			lastLocation = loc;
			lastTime = new Date();
			showLocation(); // Testing only, REMOVE
			logLocation();
			postLocation();
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}

		// This function for testing only, REMOVE!
		private void showLocation() {
			Toast.makeText(
					context,
					"Location changed - Lat: " + lastLocation.getLatitude() + " Lng: "
							+ lastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
		}

		private void logLocation() {
			String latitude = "Latitude: " + String.valueOf(lastLocation.getLatitude());
			String longitude = "Longitude: "
					+ String.valueOf(lastLocation.getLongitude());
			String timeGMT = "GMT Time: " + dateToGMTString(lastTime);
			Log.v(TAG, latitude + ", " + longitude + ", " + timeGMT);
		}

		private void postLocation() {
			
			// Create the post request.
			HttpPost request = new HttpPost(POST_URL);
			String patientID = "4";  // TODO: TESTING ONLY!
			String latitude = String.valueOf(lastLocation.getLatitude());
			String longitude = String.valueOf(lastLocation.getLongitude());
			
			JSONObject jObj = new JSONObject();
			try {
				jObj.put("patientID", patientID);
				jObj.put("latitude", latitude);
				jObj.put("longitude", longitude);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			try {
				StringEntity se = new StringEntity(jObj.toString());
				request.setHeader("Accept", "application/json");
			    request.setHeader("Content-type", "application/json");
				request.setEntity(se);
				new NetworkTask().execute(request);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	private String dateToGMTString(Date time) {
		SimpleDateFormat dfGMT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dfGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dfGMT.format(time);
	}
}
