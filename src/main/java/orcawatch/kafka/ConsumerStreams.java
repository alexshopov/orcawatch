package orcawatch.kafka;

import java.util.Arrays;
import java.util.Properties;

import orcawatch.classifier.FrequencyOrca;
import orcawatch.classifier.SignalEventClassifier;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import orcawatch.signal.SignalEvent;
import orcawatch.signal.SignalEventDeserializer;
import orcawatch.signal.SignalEventSerializer;
import sun.misc.Signal;

public class ConsumerStreams {
    String kafkaUrl;
    String kafkaTopic;
    String consumerName;
    KafkaStreams processingStream;
    public static Serde<SignalEvent> SIGNAL_EVENT_SERDE =
            Serdes.serdeFrom(new SignalEventSerializer(), new SignalEventDeserializer());

    public ConsumerStreams() {
        kafkaTopic = System.getProperty("kafka_topic", "test_topic");
        kafkaUrl = System.getProperty("kafka_url", "localhost:9092");
        consumerName = System.getProperty("kafka_consumer_id", "orcawatch-consumer");

        String signalTypeCountTopic =
                System.getProperty("sensor_type_count_topic", "sensor_type_count_topic");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, consumerName);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.TOPOLOGY_OPTIMIZATION, "all");

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, SignalEvent> stream = builder.stream(kafkaTopic, Consumed.with(Serdes.String(), SIGNAL_EVENT_SERDE));

        FrequencyOrca frequencyOrca = new FrequencyOrca();
        SignalEventClassifier signalEventClassifier = new SignalEventClassifier(frequencyOrca);
        KTable<String, Long> countByHydrophone = stream
                .flatMapValues(signal -> Arrays.asList(signal.getCoords() + " : " + signalEventClassifier.classify(signal)))
                .groupBy((key, value) -> value)
                .count();

        countByHydrophone
                .toStream()
                .mapValues((key, value) -> key + ": " + value.toString())
                .to(signalTypeCountTopic, Produced.with(Serdes.String(), Serdes.String()));
//        countByHydrophone.toStream()
//                .mapValues((key, values) -> key + " : " + values.toString())
//                .to(signalTypeCountTopic, Produced.with(Serdes.String(), Serdes.String()));

//        KGroupedStream<String, SignalEvent> countByHydrophone = stream
//                .map((key, value) -> KeyValue.pair(value.getSite(), value))
//                .groupByKey();
//        stream.groupBy((key, value) -> KeyValue.pair(value.getSite(), value));

        final Topology topology = builder.build();
        System.out.println(topology.describe());

        processingStream = new KafkaStreams(topology, props);
        Runtime.getRuntime().addShutdownHook(new Thread(processingStream::close));
    }

    public void start() {
        processingStream.start();
    }

    public static void main(String[] args) {
        ConsumerStreams consumerStreams = new ConsumerStreams();
        consumerStreams.start();
    }
}
