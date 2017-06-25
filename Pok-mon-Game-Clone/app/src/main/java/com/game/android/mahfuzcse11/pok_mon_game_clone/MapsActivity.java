package com.game.android.mahfuzcse11.pok_mon_game_clone;

import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        CheckUserPermsions();
        LoadPockemon();
    }


    //access to permsions
    void CheckUserPermsions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        runLocationLisener(); //getLastLocation();// init the contact list

    }

    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    runLocationLisener(); //  getLastLocation();// init the contact list
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Access Denailed???", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    class MyThread extends Thread {

        public void run() {


            while (true) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mMap.clear();
                        if (LocationLisener.location != null) {
                            // Add a marker in Sydney and move the camera
                            LatLng sydney = new LatLng(LocationLisener.location.getLatitude(), LocationLisener.location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(sydney).title("Player Location").icon(
                                    BitmapDescriptorFactory.fromResource(R.drawable.player)
                            ));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                            for (int i = 0; i < pockemons.size(); i++) {

                                Pockemon pockemon = pockemons.get(i);

                                LatLng pockemonLocation = new LatLng(pockemon.location.getLatitude(), pockemon.location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(pockemonLocation)
                                        .title(pockemon.name)
                                        .snippet(pockemon.des + "Power : " + pockemon.power)
                                        .icon(
                                                BitmapDescriptorFactory.fromResource(pockemon.Image)
                                        ));


                            }
                        }

                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    ArrayList<Pockemon> pockemons = new ArrayList<>();

    void LoadPockemon() {

        pockemons.add(new Pockemon(R.drawable.bullbasaur, "bullbasaur", "This is a bullbasaur", 40, 33.33, 44.44));
        pockemons.add(new Pockemon(R.drawable.jigglypuff, "jigglypuff", "This is a jigglypuff", 60, 34.33, 40.44));
        pockemons.add(new Pockemon(R.drawable.pikachu, "pikachu", "This is a pikachu", 100, 43.33, 74.44));
    }


    void runLocationLisener() {

        LocationListener locationListener = new LocationLisener();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 5, locationListener);

        MyThread myThread = new MyThread();
        myThread.start();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}
