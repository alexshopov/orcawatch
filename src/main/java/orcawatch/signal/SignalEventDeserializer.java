package orcawatch.signal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class SignalEventDeserializer implements Deserializer<SignalEvent> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SignalEvent deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        SignalEvent signalEvent;
        try {
            signalEvent = objectMapper.readValue(data, SignalEvent.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return signalEvent;
    }
}
