package com.longbuffer.locationdector;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class LocationDetector {
    Timer timer1;
    LocationManager lm;
    LocationResult locationResult;
    boolean gps_enabled=false;
    boolean network_enabled=false;
    boolean locationTimeOut = false;
    boolean locationTimeIn = false;
    
    boolean isAlreadyRequested =false;

    public boolean getLocation(Context context, LocationResult result)
    {
        //I use LocationResult callback class to pass location value from MyLocation to user code.
    	
    	if(isAlreadyRequested){
    		lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);
    	}
    	
        locationResult=result;
        locationTimeOut = false ;
        locationTimeIn = false ;
        if(lm==null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //exceptions will be thrown if provider is not permitted.
        try{gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(!gps_enabled && !network_enabled){
        	if(locationResult != null){
        		locationResult.LocationReaderError();
        	}
            return false;
        }

        if(gps_enabled)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if(network_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        timer1=new Timer();
        timer1.schedule(new GetLastLocation(), 20000);
        isAlreadyRequested = true;
        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            locationTimeIn = true ;
            locationResult.gotLocation(location);
            //lm.removeUpdates(this);
            //lm.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            locationTimeIn = true ;
            locationResult.gotLocation(location);
            //lm.removeUpdates(this);
            //lm.removeUpdates(locationListenerGps);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    public boolean getTimeOutStatus(){
    	return locationTimeOut;
    }
    public boolean getTimeInStatus(){
    	return locationTimeIn;
    }
    class GetLastLocation extends TimerTask {
        @Override
        public void run() {        	
             //lm.removeUpdates(locationListenerGps);
             //lm.removeUpdates(locationListenerNetwork);
/*
             Location net_loc=null, gps_loc=null;
             if(gps_enabled)
                 gps_loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             if(network_enabled)
                 net_loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

             //if there are both values use the latest one
             if(gps_loc!=null && net_loc!=null){
                 if(gps_loc.getTime()>net_loc.getTime())
                     locationResult.gotLocation(gps_loc);
                 else
                     locationResult.gotLocation(net_loc);
                 return;
             }

             if(gps_loc!=null){
                 locationResult.gotLocation(gps_loc);
                 return;
             }
             if(net_loc!=null){
                 locationResult.gotLocation(net_loc);
                 return;
             }
             locationResult.gotLocation(null);
             */
             locationResult.LocationReaderError();
             locationTimeOut = true ;
        }
    }

    public static abstract class LocationResult{
        public abstract void gotLocation(Location location);
        public abstract void LocationReaderError();
    }   
    
    public void checkStatus(){
    	while(true){
    		if(locationTimeOut || locationTimeIn){
    			break;
    		}
    		if(locationTimeOut){
    			locationResult.LocationReaderError();
    		}
    	}
    }
    
}
