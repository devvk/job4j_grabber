package ru.job4j.grabber;

import io.javalin.Javalin;
import ru.job4j.grabber.parser.HabrCareerParser;
import ru.job4j.grabber.parser.Parser;
import ru.job4j.grabber.repository.JdbcStore;
import ru.job4j.grabber.repository.Store;
import ru.job4j.grabber.scheduler.SchedulerManager;
import ru.job4j.grabber.scheduler.SuperJobGrab;
import ru.job4j.grabber.utils.Config;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        config.load("app.properties");

        Store store = new JdbcStore(config);
        Parser parser = new HabrCareerParser(new HabrCareerDateTimeParser());

        SchedulerManager scheduler = new SchedulerManager();
        scheduler.init();
        scheduler.load(
                Integer.parseInt(config.get("rabbit.interval")),
                SuperJobGrab.class,
                store,
                parser);


        startServer(Integer.parseInt(config.get("server.port")), store);
    }

    public static void startServer(int port, Store store) {
        Javalin server = Javalin.create();
        server.start(port);

        StringBuilder vacancies = new StringBuilder();
        store.getAll().forEach(post -> vacancies.append(post.toString())
                .append(System.lineSeparator())
                .append(System.lineSeparator())
        );

        server.get("/", ctx -> {
            ctx.contentType("text/plain; charset=UTF-8");
            ctx.result(vacancies.toString());
        });
    }
}
