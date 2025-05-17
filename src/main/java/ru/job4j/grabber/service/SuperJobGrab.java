package ru.job4j.grabber.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.stores.Store;

public class SuperJobGrab implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        Store store = (Store) context.getJobDetail().getJobDataMap().get("store");
        for (Post post : store.getAll()) {
            System.out.println(post);
        }
    }
}
