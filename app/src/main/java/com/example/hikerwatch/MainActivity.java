package com.example.hikerwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener listener;
    Double longitude;
    Double latitude;
    Double altitude;
    TextView longitude1;
    TextView latitude1;
    TextView altitude1;
    Geocoder geocoder;
    TextView address1;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,listener);
        }

    }

    }

    public void start_listenting()
    {
        longitude1=(TextView) findViewById(R.id.longitude);
        latitude1=(TextView)findViewById(R.id.latitude);
        altitude1=(TextView)findViewById(R.id.elevation);
        address1=(TextView) findViewById(R.id.address);
        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        listener= new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                longitude=location.getLongitude();
                latitude=location.getLatitude();
                altitude=location.getAltitude();

                longitude1.setText(longitude.toString());
                latitude1.setText(latitude.toString());
                altitude1.setText(altitude.toString());
                String address="could not find address";

                try{
                    List<Address>listaddress= geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(listaddress!=null &&listaddress.size()>0)
                    {
                        address=" ";
                        if(listaddress.get(0).getThoroughfare()!=null)
                        {
                            address+=listaddress.get(0).getThoroughfare()+"\n";
                        }
                        if(listaddress.get(0).getLocality()!=null)
                        {
                            address+=listaddress.get(0).getLocality()+"\n";
                        }
                    }
                    address1.setText(address);


                }catch (Exception e)
                {
                    e.printStackTrace();
                }









            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,listener);
        }

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        geocoder= new Geocoder(this,Locale.getDefault());
        start_listenting();




    }


}