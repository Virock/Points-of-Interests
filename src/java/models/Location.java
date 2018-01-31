package models;

public class Location {

    private String lon, lat;
    private int ID;

    public Location(String lon, String lat, int ID) {
        this.lon = lon;
        this.lat = lat;
        this.ID = ID;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public int getID() {
        return ID;
    }
}
