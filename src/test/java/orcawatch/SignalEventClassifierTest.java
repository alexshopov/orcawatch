package orcawatch;

import orcawatch.classifier.FrequencyOrca;
import orcawatch.data.Hydrophone;
import orcawatch.signal.SignalEvent;
import orcawatch.classifier.SignalEventClassifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SignalEventClassifierTest {
//    Hydrophone sampleHydrophone = new Hydrophone(new String[]{"1", ".1", ".1"});

    @Test
    public void testSignalClassifier() {
        FrequencyOrca frequencyOrca = new FrequencyOrca();
        SignalEvent testSignalEvent = new SignalEvent();
        testSignalEvent.setFrequency(5000);

        SignalEventClassifier signalEventClassifier = new SignalEventClassifier(frequencyOrca);
        Assertions.assertTrue(signalEventClassifier.classify(testSignalEvent) == "orca");
    }
}
