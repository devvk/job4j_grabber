package ru.job4j.grabber;

import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.service.Config;
import ru.job4j.grabber.service.SchedulerManager;
import ru.job4j.grabber.service.SuperJobGrab;
import ru.job4j.grabber.stores.JdbcStore;
import ru.job4j.grabber.stores.Store;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        config.load("app.properties");

        Store store = new JdbcStore(config);
        Post post = new Post(1, "Java Job", "http://link.com", "description", LocalDateTime.now());
        store.save(post);

        var scheduler = new SchedulerManager();
        scheduler.init();
        scheduler.load(
                Integer.parseInt(config.get("rabbit.interval")),
                SuperJobGrab.class,
                store);
    }
}
