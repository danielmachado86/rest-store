package io.dmcapps.dshopping.store.address;

import java.util.ArrayList;

public class Location {

    public String type = "Point";
    public ArrayList<Double> coordinates;

    public Location(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
    public Location() {
    }

}
