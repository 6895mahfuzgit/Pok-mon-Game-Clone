package com.game.android.mahfuzcse11.pok_mon_game_clone;

import android.location.Location;

/**
 * Created by MahfuzCSE'11 on 25-Jun-17.
 */

public class Pockemon {
    public int Image;
    public String name;
    public String des;
    public double power;
    public boolean isCatch;
    public Location location;


    public Pockemon(int image, String name, String des, double power, double log, double lat) {
        Image = image;
        this.name = name;
        this.des = des;
        this.power = power;
        this.isCatch = false;

        location = new Location(name);
        location.setLatitude(lat);
        location.setLongitude(log);
    }
}
