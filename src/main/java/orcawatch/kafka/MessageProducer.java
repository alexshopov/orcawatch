package orcawatch.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProducer {
    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private Producer<byte[], byte[]> producer;
    private Properties props;
    private String topic;
    private String bootstrapUrl;

    public MessageProducer(String bootstrapUrl, String topic) {
        this.topic = topic;
        this.bootstrapUrl = bootstrapUrl;

        props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapUrl);

        producer = new KafkaProducer<byte[], byte[]>(props,
                new ByteArraySerializer(), new ByteArraySerializer());

        logger.info("MessageProducer initialized with properties: {}", props);
    }

    public void sendMessageByKey(String dest, String key, String body) {
        ProducerRecord<byte[], byte[]> record =
                new ProducerRecord<>(dest, key.getBytes(), body.getBytes());
        producer.send(record);

        logger.debug("sent message >>>\n " + record);
    }

    public void sendMessage(String dest, String body) {
        String key = String.valueOf(body.hashCode());
        sendMessageByKey(dest, key, body);
    }

    public void closeProducer() {
        producer.close();
    }
}
