package orcawatch.classifier;

import orcawatch.data.FrequencyRange;
import orcawatch.signal.SignalEvent;
import orcawatch.utils.ConfigHelper;

import java.util.Properties;

public class FrequencyShip {
    private FrequencyRange frequencyRange;

    public FrequencyShip() {
        ConfigHelper configHelper = new ConfigHelper();
        frequencyRange = new FrequencyRange();

        Properties props = configHelper.readProperties();

        frequencyRange.setFreqMin(Integer.parseInt(props.getProperty("ship.min")));
        frequencyRange.setFreqMax(Integer.parseInt(props.getProperty("ship.max")));
        frequencyRange.setFreqPeak(Integer.parseInt(props.getProperty("ship.peak")));
    }

    public FrequencyRange getFrequencyRange() {
        return frequencyRange;
    }

    public boolean isOrca(SignalEvent signalEvent) {
        return signalEvent.getFrequency() > frequencyRange.getFreqMin() &&
                signalEvent.getFrequency() < frequencyRange.getFreqMax();
    }
}
