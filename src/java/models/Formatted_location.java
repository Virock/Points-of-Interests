
package models;

public class Formatted_location {
    private String location;
    private int distance;

    public Formatted_location(String location, int distance) {
        this.location = location;
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public int getDistance() {
        return distance;
    }
    
}
