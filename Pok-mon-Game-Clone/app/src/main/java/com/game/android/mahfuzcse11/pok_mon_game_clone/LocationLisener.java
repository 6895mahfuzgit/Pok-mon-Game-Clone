package com.game.android.mahfuzcse11.pok_mon_game_clone;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by MahfuzCSE'11 on 25-Jun-17.
 */

public class LocationLisener implements LocationListener {


    public static Location location;

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
