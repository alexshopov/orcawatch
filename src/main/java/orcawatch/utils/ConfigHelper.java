package orcawatch.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {
    public Properties readProperties() {
        Properties props = new Properties();

        InputStream inp = getClass().getClassLoader().getResourceAsStream("config.properties");

        try {
            props.load(inp);
        } catch (IOException e) {

        }

        return props;
    }
}
