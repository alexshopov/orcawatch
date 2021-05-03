package orcawatch.classifier;

import java.util.Properties;

import orcawatch.data.FrequencyRange;
import orcawatch.signal.SignalEvent;
import orcawatch.utils.ConfigHelper;

public class FrequencyOrca {
    private FrequencyRange frequencyRange;

    public FrequencyOrca() {
        ConfigHelper configHelper = new ConfigHelper();
        frequencyRange = new FrequencyRange();

        Properties props = configHelper.readProperties();

        frequencyRange.setFreqMin(Integer.parseInt(props.getProperty("orca.min")));
        frequencyRange.setFreqMax(Integer.parseInt(props.getProperty("orca.max")));
        frequencyRange.setFreqPeak(Integer.parseInt(props.getProperty("orca.peak")));
    }

    public FrequencyRange getFrequencyRange() {
        return frequencyRange;
    }

    public boolean isOrca(SignalEvent signalEvent) {
        return signalEvent.getFrequency() > frequencyRange.getFreqMin() &&
                signalEvent.getFrequency() < frequencyRange.getFreqMax();
    }
}

