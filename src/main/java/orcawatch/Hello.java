package orcawatch;

public class Hello {
    public static void main(String[] args) {
        SignalClassifier signalClassifier = new SignalClassifier();

        System.out.println("Classify = " + signalClassifier.classify(1));
    }
}
