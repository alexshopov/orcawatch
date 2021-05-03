package orcawatch.data;

public class FrequencyRange {
    private int freqMin;
    private int freqMax;
    private int freqPeak;

    public FrequencyRange() { }

    public FrequencyRange(String[] frequencies) {
        this.freqMin = Integer.parseInt(frequencies[0]);
        this.freqMax = Integer.parseInt(frequencies[1]);
        this.freqPeak = Integer.parseInt(frequencies[2]);
    }

    @Override
    public String toString() {
        return freqMin + ", " + freqMax + ", " + freqPeak;
    }

    public int getFreqMin() {
        return freqMin;
    }

    public void setFreqMin(int freqMin) {
        this.freqMin = freqMin;
    }

    public int getFreqMax() {
        return freqMax;
    }

    public void setFreqMax(int freqMax) {
        this.freqMax = freqMax;
    }

    public int getFreqPeak() {
        return freqPeak;
    }

    public void setFreqPeak(int freqPeak) {
        this.freqPeak = freqPeak;
    }
}
