package orcawatch.data;

public class Hydrophone {
    private String id;
    private String latitude;
    private String longitude;

    public Hydrophone(String[] coords) {
        id = coords[0];
        latitude = coords[1];
        longitude = coords[2];
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Hydrophone)) {
            return false;
        }

        Hydrophone h = (Hydrophone) o;

        return latitude.compareTo(h.latitude) == 0 && longitude.compareTo(h.longitude) == 0;
    }

    @Override
    public String toString() {
        return latitude + ", " + longitude;
    }

    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
