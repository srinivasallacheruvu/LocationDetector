package com.longbuffer.locationdector;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.longbuffer.locationdector.LocationDetector.LocationResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationDectorActivity extends Activity {


	TextView titlelocation, textOut,textaddr;
	TextView textsubadmin, 
	textadmin, 
	textcountryname,
	textfeaturename,
	textsublocality,
	textlocality, 
	textphone,
	textpostalcode,
	textpremises;
	TextView titleBar;
	String provider;// 
	String space = "      ";
	Geocoder mGeocoder;
	
	LocationDetector mLocationDetector;
	LocationResult mLocationResult;
	
	Button mRefreshBtn;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_detector_view);
		
		
		titleBar=(TextView) getWindow().findViewById(android.R.id.title);
        titleBar.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        if(titleBar!=null){
       	 titleBar.setTextColor(Color.YELLOW);
       	titleBar.setText("Location Detector");
       	 ViewParent parent=titleBar.getParent();
       	 if(parent!=null){
       		View parentview=(View) parent; 
       		parentview.setBackgroundColor(Color.rgb(0x55, 0x55, 0x55));
       	 }
        }
        titlelocation = (TextView) findViewById(R.id.titlelocation);
       textOut = (TextView) findViewById(R.id.textOut);
       textaddr=(TextView) findViewById(R.id.textaddr);
       
       textsubadmin=(TextView) findViewById(R.id.textsubadmin);
       textadmin=(TextView) findViewById(R.id.textadmin);
       textcountryname=(TextView) findViewById(R.id.textcountryname);
       textfeaturename=(TextView) findViewById(R.id.textfeaturename);
       textsublocality=(TextView) findViewById(R.id.textsublocality);
       textlocality=(TextView) findViewById(R.id.textlocality);
       textphone=(TextView) findViewById(R.id.textphone);
       textpostalcode=(TextView) findViewById(R.id.textpostalcode);
       textpremises=(TextView) findViewById(R.id.textpremises);
       
            
       mRefreshBtn = (Button)findViewById(R.id.refresh);
       
       mRefreshBtn.setOnClickListener(new View.OnClickListener() {
    	   @Override
    	   public void onClick(View v) {
    		   
    		   LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
    			if(!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
    			{
    				enableGPS();
    				setTextViewData("No Data");
    				return;
    			}
    		   
    		   mRefreshBtn.setEnabled(false);
    		   mRefreshBtn.setText("Getting Location");
    		   setTextViewData("Loading...");
    		   mLocationDetector.getLocation(LocationDectorActivity.this, mLocationResult);
    		   }
       });
       
       
       mLocationDetector = new LocationDetector();
       mLocationResult = new LocationResult(){

		@Override
		public void gotLocation(Location location) {
			 
		       String text = String.format(
		           "Latitude-->\t %f\nLongitude-->\t %f\nAltitude-->\t %f\nBearing-->\t %f", location
		               .getLatitude(), location.getLongitude(), location.getAltitude(),
		           location.getBearing()); // 
		       //textOut.setText(text);
		       
		       titlelocation.setText("");
				textOut.setText("");
		       String tempText = "";

		       // Perform geocoding for this location
		       mGeocoder = new Geocoder(LocationDectorActivity.this,Locale.getDefault()); 
		       List<Address> addresses=null;
		       try {
		         addresses = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); 
		         
		         
		         
		          for (Address address : addresses) {
		        	  
		        	  tempText = "";
		        	  for(int i =0 ; i < address.getMaxAddressLineIndex() ;i++){
		        		  if(i == address.getMaxAddressLineIndex() -1 ){
		        			  tempText = tempText  +space+ address.getAddressLine(i) ;
		        		  }else{
		        			  tempText = tempText  +space+ address.getAddressLine(i) +"\n";
		        		  }
		              }
		        	  textaddr.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getSubAdminArea();
		        	  textsubadmin.setText(tempText);
		        	  
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getAdminArea();
		        	  textadmin.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getAdminArea() +"-" +address.getCountryCode();
		        	  textadmin.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getCountryName() ;
		        	  textcountryname.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getFeatureName() ;
		        	  textfeaturename.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getSubLocality() ;
		        	  textsublocality.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getLocality() ;
		        	  textlocality.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getPhone() ;
		        	  textphone.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getPostalCode() ;
		        	  textpostalcode.setText(tempText);
		        	  
		        	  tempText = "";
		        	  tempText = tempText + space +  address.getPremises() ;
		        	  textpremises.setText(tempText);	
		        	  break;
		          }		          
		       } catch (IOException e) {
		         Log.e("WhereAmI", "Couldn't get Geocoder data", e);
		       }
		       mRefreshBtn.setEnabled(true);
		       mRefreshBtn.setText("Get Location");
		}

		@Override
		public void LocationReaderError() {
			Toast.makeText(LocationDectorActivity.this, "Unable to get the location. \n Connection Error.", Toast.LENGTH_LONG).show();
			setTextViewData("No Data");
			mRefreshBtn.setEnabled(true);
			mRefreshBtn.setText("Get Location");
		}    	   
       };
       
       LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
		if(!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
		{
			enableGPS();
			setTextViewData("No Data");
			return;
		}
		mLocationDetector.getLocation(this, mLocationResult);
		mRefreshBtn.setEnabled(false);
		mRefreshBtn.setText("Getting Location");
		setTextViewData("Loading...");
		
		titlelocation.setVisibility(View.INVISIBLE);
		textOut.setVisibility(View.INVISIBLE);
		
		titlelocation.setText("");
		textOut.setText("");

	}
	
	public void enableGPS(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage(
	            "Your GPS seems to be disabled, do you want to enable it?")
	            .setCancelable(false).setPositiveButton("Yes",
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(
	                                @SuppressWarnings("unused") final DialogInterface dialog,
	                                @SuppressWarnings("unused") final int id) {
	                            Intent intent = new Intent(
	                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                            startActivity(intent);
	                        }
	                    }).setNegativeButton("No",
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(final DialogInterface dialog,
	                                @SuppressWarnings("unused") final int id) {
	                            dialog.cancel();
	                        }
	                    });
	    final AlertDialog alert = builder.create();
	    alert.show();

	}
	
	public void setTextViewData(String text){
		String tempText = text;
		//textOut.setText(tempText);
		textaddr.setText(tempText);
		textsubadmin.setText(tempText);
		textadmin.setText(tempText);
		textadmin.setText(tempText);
		textcountryname.setText(tempText);
		textfeaturename.setText(tempText);
		textsublocality.setText(tempText);
		textlocality.setText(tempText);
		textphone.setText(tempText);
		textpostalcode.setText(tempText);
		textpremises.setText(tempText);
	}
	
}