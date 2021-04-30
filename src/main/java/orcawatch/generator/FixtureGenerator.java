package orcawatch.generator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import orcawatch.model.FrequencyRange;
import orcawatch.model.Hydrophone;
import orcawatch.model.IDataHelper;
import orcawatch.model.Signal;


public class FixtureGenerator {
    List<Hydrophone> hydrophones;

    FrequencyRange freqOrca;
    FrequencyRange freqShip;

    List<Signal> signals;
    int numSignals = 10;

    List<Object> readInput(String filename, IDataHelper dataHelper) {
        List<Object> list = null;

        try {
            Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());

            Stream<String> stream = Files.lines(path);
            list = stream
                    .filter(line -> StringUtils.isNotBlank(line) && !line.startsWith("#"))
                    .map(line -> dataHelper.dataConversion(line))
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            System.out.println(e);
        }

        return list;
    }

    String[] splitLine(String line) {
        return StringUtils.split(line, StringUtils.defaultString(","));
    }

    public void generateData() {
        hydrophones = readInput(
            "fixture/hydrophone-locations.txt",
                    (line) -> new Hydrophone(splitLine(line))
                )
                .stream()
                .map(h -> (Hydrophone) h)
                .collect(Collectors.toList());

        freqOrca = (FrequencyRange) readInput(
                "fixture/hydrophone-orca.txt",
                (line) -> new FrequencyRange(splitLine(line))
        ).get(0);

        freqShip = (FrequencyRange) readInput(
                "fixture/hydrophone-ship.txt",
                (line) -> new FrequencyRange(splitLine(line))
        ).get(0);

        signals = new ArrayList<>(numSignals);
        for (int i = 0; i < numSignals; ++i) {
            int hydrophoneIdx = ThreadLocalRandom.current().nextInt(hydrophones.size());
            int sigType = ThreadLocalRandom.current().nextInt(0, 2);

            int sigMin, sigMax;
            if (sigType == 0) {
                sigMin = freqOrca.getFreqMin();
                sigMax = freqOrca.getFreqMax();
            } else {
                sigMin = freqShip.getFreqMin();
                sigMax = freqShip.getFreqMax();
            }
            int freq = ThreadLocalRandom.current().nextInt(sigMin, sigMax);

            signals.add(new Signal( hydrophones.get(hydrophoneIdx), freq));
        }

        signals.forEach(s -> System.out.println(s.toString()));
    }

    public static void main(String[] args) {
        FixtureGenerator fixtureGenerator = new FixtureGenerator();
        fixtureGenerator.generateData();
    }
}
