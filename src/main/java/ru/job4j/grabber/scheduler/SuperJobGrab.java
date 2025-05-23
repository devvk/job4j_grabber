package ru.job4j.grabber.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.parser.Parser;
import ru.job4j.grabber.repository.Store;

import java.util.List;

public class SuperJobGrab implements Job {

    /**
     * Выполняет задачу парсинга и сохраняет данные.
     *
     * @param context контекст выполнения задачи, содержащий параметры
     */
    @Override
    public void execute(JobExecutionContext context) {
        Store store = (Store) context.getJobDetail().getJobDataMap().get("store");
        Parser parser = (Parser) context.getJobDetail().getJobDataMap().get("parser");

        List<Post> vacancies = parser.fetch();
        for (Post post : vacancies) {
            store.save(post);
            System.out.println(post);
        }
    }
}
