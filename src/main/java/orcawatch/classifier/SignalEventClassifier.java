package orcawatch.classifier;

import orcawatch.signal.SignalEvent;

public class SignalEventClassifier {
    FrequencyOrca frequencyOrca;

    public SignalEventClassifier(FrequencyOrca frequencyOrca) {
        this.frequencyOrca = frequencyOrca;
    }

    public String classify(SignalEvent signalEvent) {
        if (frequencyOrca.isOrca(signalEvent)) {
            return "orca";
        }

        return "ship";
    }
}
