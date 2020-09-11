package com.asgarov.elevator_challenge.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesReader {

    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";

    @SneakyThrows
    public static Properties getProperties(String fileName) {
        try (InputStream input = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {

            Properties prop = new Properties();
            if (input == null) {
                log.error(fileName + " not found on classpath");
                throw new IllegalArgumentException();
            }
            prop.load(input);
            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        throw new FileNotFoundException();
    }

    public static Properties getProperties() {
        return getProperties(DEFAULT_PROPERTIES_FILE);
    }
}
