package orcawatch.signal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class SignalEventSerializer implements Serializer<SignalEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, SignalEvent signalEvent) {
        if (signalEvent == null)
            return null;

        try {
            return objectMapper.writeValueAsBytes(signalEvent);
        } catch (Exception e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }
}
