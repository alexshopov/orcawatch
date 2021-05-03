package orcawatch.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import orcawatch.signal.SignalEvent;

public class JsonHelper {
    public static String toJson(ObjectMapper objectMapper, SignalEvent signalEvent) {
        try {
            return objectMapper.writeValueAsString(signalEvent);
        } catch (JsonProcessingException jpe) {
            System.out.println(jpe);
        }
        return "";
    }
}
