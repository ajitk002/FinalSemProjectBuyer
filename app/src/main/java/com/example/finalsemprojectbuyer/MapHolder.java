package com.example.finalsemprojectbuyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapHolder extends AppCompatActivity
{
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LocationRequestCode = 1234;
    private boolean permissionGranted = false;
    int PLACE_PICKER_REQUEST = 1;
    private GoogleMap gMap ;
    Fragment map ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_holder);

    }
    private void getDeviceLocation(){

    }
    private void initMap()
    {
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Toast.makeText(getApplicationContext(),"Map is ready to use",Toast.LENGTH_SHORT).show();
                    gMap = googleMap;
            }
        });
    }
    private void getLocationPermission(){
        String[] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                permissionGranted = true;
                initMap();
            }
        }
        else{
            ActivityCompat.requestPermissions(this , permission,LocationRequestCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case LocationRequestCode :
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    int i;
                    for (i = 0; i<grantResults.length; i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                        {
                            permissionGranted = false;
                            return;
                        }
                    }
                    permissionGranted = true;
                    initMap();
                }
        }
    }
}
