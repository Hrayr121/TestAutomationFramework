package com.testframework.core.config;

import com.testframework.core.enums.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();
    private static final Properties envProps = new Properties();

    static {
        try {
            try (InputStream baseInput = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties")) {
                if (baseInput == null) {
                    throw new RuntimeException("config.properties not found on classpath");
                }
                props.load(baseInput);
            }

            String env = System.getProperty("env", props.getProperty("env", "staging"));
            String envConfigFile = "config-" + env + ".properties";
            try (InputStream envInput = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream(envConfigFile)) {
                if (envInput != null) {
                    envProps.load(envInput);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static String get(String key) {
        String value = envProps.getProperty(key);
        if (value != null) {
            return value;
        }
        value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing config key: " + key);
        }
        return value;
    }

    public static int getInt(String key) {
        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Config key '" + key + "' is not a valid integer", e);
        }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static Environment getEnvironment() {
        String env = System.getProperty("env", props.getProperty("env", "staging"));
        return Environment.fromString(env);
    }
}
