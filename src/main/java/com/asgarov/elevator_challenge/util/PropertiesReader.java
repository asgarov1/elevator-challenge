package com.asgarov.elevator_challenge.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesReader {

    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";

    /**
     * Convenience method to read default properties file if no other file name was specified
     *
     * @return Properties
     */
    public static Properties getProperties() {
        return getProperties(DEFAULT_PROPERTIES_FILE);
    }

    /**
     * Method to read specified properties file
     *
     * @param fileName is the file that will be looked for
     * @return Properties
     */
    @SneakyThrows
    public static Properties getProperties(String fileName) {
        try (InputStream input = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {
            Properties properties = new Properties();
            if (input == null) {
                log.error(fileName + " not found on classpath");
                throw new IllegalArgumentException();
            }
            properties.load(input);
            return properties;
        }
    }
}
