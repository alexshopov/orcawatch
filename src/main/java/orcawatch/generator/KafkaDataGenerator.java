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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import orcawatch.kafka.MessageProducer;
import orcawatch.model.FrequencyRange;
import orcawatch.model.Hydrophone;
import orcawatch.model.IDataHelper;
import orcawatch.model.Signal;


public class KafkaDataGenerator {
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final String DEFAULT_TEST_TOPIC = "test";
    public static final String DEFAULT_KAFKA_URL = "localhost:9092";
    private String kafkaTopic ;
    private String kafkaUrl ;

    List<Hydrophone> hydrophones;

    FrequencyRange freqOrca;
    FrequencyRange freqShip;

    List<Signal> signals;
    int numSignals = 10;

    MessageProducer kafkaProducer = null ;

    public KafkaDataGenerator() {
        kafkaTopic = System.getProperty("kafka_topic", DEFAULT_TEST_TOPIC);
        kafkaUrl = System.getProperty("kafka_url", DEFAULT_KAFKA_URL);
        kafkaProducer = new MessageProducer(kafkaUrl, kafkaTopic);
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaProducer::closeProducer));
    }

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

            Signal signalEvent = new Signal(hydrophones.get(hydrophoneIdx), freq);

            try {
                kafkaProducer.sendMessage(kafkaTopic, toJson(signalEvent));
            } catch (JsonProcessingException jpe) {
                System.out.println(jpe);
            }
        }
    }

    String toJson(Signal signalEvent) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(signalEvent);
    }

    public static void main(String[] args) {
        KafkaDataGenerator fixtureGenerator = new KafkaDataGenerator();
        fixtureGenerator.generateData();
    }
}
