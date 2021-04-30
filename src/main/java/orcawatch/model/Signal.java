package orcawatch.model;

import java.util.Calendar;
import java.util.UUID;

import orcawatch.model.Hydrophone;

public class Signal {
    private UUID signalId;
    private Hydrophone hydrophone;
    private Calendar calendar;
    private int frequency;
    private int duration;

    public Signal(Hydrophone hydrophone, int frequency) {
        this.signalId = UUID.randomUUID();
        this.hydrophone = hydrophone;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return hydrophone.toString() + ": " + frequency;
    }

    public UUID getSignalId() {
        return signalId;
    }

    public Hydrophone getHydrophone() {
        return hydrophone;
    }

    public void setHydrophone(Hydrophone hydrophone) {
        this.hydrophone = hydrophone;
    }

    public void setSignalId(UUID signalId) {
        this.signalId = signalId;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
