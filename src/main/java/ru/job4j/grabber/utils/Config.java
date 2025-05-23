package ru.job4j.grabber.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Класс используется для загрузки настроек:
 * - настройки соединения с базой данных
 * - настройка периодичности
 */
public class Config {
    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private final Properties properties = new Properties();

    /**
     * Загружает свойства из указанного файла.
     *
     * @param file имя файла.
     */
    public void load(String file) {
        try (var input = Config.class.getClassLoader().getResourceAsStream(file)) {
            properties.load(input);
        } catch (IOException e) {
            LOG.error("Failed to load file: {}", file, e);
        }
    }

    /**
     * Возвращает значение свойства по ключу.
     *
     * @param key название ключа
     * @return значение свойства
     */
    public String get(String key) {
        return properties.getProperty(key);
    }
}
