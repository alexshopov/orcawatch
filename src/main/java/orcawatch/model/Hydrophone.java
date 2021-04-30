package orcawatch.model;

public class Hydrophone {
    private float latitude;
    private float longitude;

    public Hydrophone(String[] coords) {
        latitude = Float.parseFloat(coords[0]);
        longitude = Float.parseFloat(coords[1]);
    }

    @Override
    public String toString() {
        return latitude + ", " + longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
