package orcawatch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SignalClassifierTest {
    @Test
    public void testSignalClassifier() {
        SignalClassifier signalClassifier = new SignalClassifier();
        Assertions.assertTrue(signalClassifier.classify(1) == "orca");
    }
}
