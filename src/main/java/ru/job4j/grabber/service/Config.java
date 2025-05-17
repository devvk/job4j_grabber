package ru.job4j.grabber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Для загрузки свойств:
 * - настройка периодичности
 * - настройки соединения с базой данных
 */
public class Config {
    private static final Logger LOG = LoggerFactory.getLogger(Config.class);
    private final Properties properties = new Properties();

    public void load(String file) {
        try (var input = Config.class.getClassLoader().getResourceAsStream(file)) {
            properties.load(input);
        } catch (IOException e) {
            LOG.error("Failed to load file: {}", file, e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
