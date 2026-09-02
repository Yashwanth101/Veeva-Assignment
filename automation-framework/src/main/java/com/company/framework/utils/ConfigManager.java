package com.company.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_RESOURCE = "configuration/config.properties";
    private static final Properties props = new Properties();
    private static boolean loaded;

    public static void loadConfig() {
        if (loaded) {
            return;
        }

        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_RESOURCE)) {
            if (inputStream == null) {
                throw new RuntimeException("Config file not found on classpath: " + CONFIG_RESOURCE);
            }
            props.load(inputStream);
            loaded = true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + CONFIG_RESOURCE, e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
}
