package orcawatch.signal;

public class SignalEvent {
    private String signalId;
    private String site;
    private String lat;
    private String lon;
    private String coords;
    private String ISO_DateTime_Local_start;
    private String ISO_DateTime_Local_end;
    private int frequency;

    public String getCoords() {
        return lat.toString() + ", " + lon.toString();
    }

    @Override
    public String toString() {
        return lat.toString() + ", " + lon.toString() + ": " + frequency;
    }

    public String getSignalId() {
        return signalId;
    }

    public void setSignalId(String signalId) {
        this.signalId = signalId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getISO_DateTime_Local_start() {
        return ISO_DateTime_Local_start;
    }

    public void setISO_DateTime_Local_start(String ISO_DateTime_Local_start) {
        this.ISO_DateTime_Local_start = ISO_DateTime_Local_start;
    }

    public String getISO_DateTime_Local_end() {
        return ISO_DateTime_Local_end;
    }

    public void setISO_DateTime_Local_end(String ISO_DateTime_Local_end) {
        this.ISO_DateTime_Local_end = ISO_DateTime_Local_end;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
