package orcawatch.data;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import orcawatch.classifier.FrequencyOrca;
import orcawatch.classifier.FrequencyShip;
import orcawatch.kafka.MessageProducer;
import orcawatch.signal.SignalEvent;
import orcawatch.utils.ConfigHelper;
import orcawatch.utils.FileReader;
import orcawatch.utils.JsonHelper;

public class KafkaDataGenerator {
    private MessageProducer kafkaProducer = null;
    private Properties kafkaProps;
    private String kafkaTopic ;
    private String kafkaUrl ;

    private List<Hydrophone> hydrophones;
    private FrequencyOrca frequencyOrca = new FrequencyOrca();
    private FrequencyShip frequencyShip = new FrequencyShip();

    public KafkaDataGenerator() {
        kafkaProps = new ConfigHelper().readProperties();

        kafkaTopic = System.getProperty("kafka_topic", kafkaProps.getProperty("kafka.defaultTopic"));
        kafkaUrl = System.getProperty("kafka_url", kafkaProps.getProperty("kafka.defaultUrl"));
        kafkaProducer = new MessageProducer(kafkaUrl, kafkaTopic);

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaProducer::closeProducer));
    }

    public void generateData() {
        hydrophones = FileReader.readInput(
                "fixture/hydrophone-locations.txt",
                (line) -> new Hydrophone(FileReader.splitLine(line))
            ).stream()
            .map(h -> (Hydrophone) h)
            .collect(Collectors.toList());

        int numSignals = 10;
        for (int i = 0; i < numSignals; ++i) {
            int hydrophoneIdx = ThreadLocalRandom.current().nextInt(hydrophones.size());
            int sigType = ThreadLocalRandom.current().nextInt(0, 2);

            int sigMin, sigMax;
            if (sigType == 0) {
                sigMin = frequencyOrca.getFrequencyRange().getFreqMin();
                sigMax = frequencyOrca.getFrequencyRange().getFreqMax();
            } else {
                sigMin = frequencyShip.getFrequencyRange().getFreqMin();
                sigMax = frequencyShip.getFrequencyRange().getFreqMax();
            }
            int freq = ThreadLocalRandom.current().nextInt(sigMin, sigMax);

            SignalEvent signalEvent = new SignalEvent();
            signalEvent.setSite(hydrophones.get(hydrophoneIdx).getId());
            signalEvent.setSignalId(UUID.randomUUID().toString());
            signalEvent.setLat(hydrophones.get(hydrophoneIdx).getLatitude());
            signalEvent.setLon(hydrophones.get(hydrophoneIdx).getLongitude());
            signalEvent.setFrequency(freq);

            ObjectMapper objectMapper = new ObjectMapper();
            kafkaProducer.sendMessage(kafkaTopic, JsonHelper.toJson(objectMapper, signalEvent));
        }
    }

    public static void main(String[] args) {
        KafkaDataGenerator fixtureGenerator = new KafkaDataGenerator();
        fixtureGenerator.generateData();
    }
}
