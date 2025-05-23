package ru.job4j.grabber.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.grabber.parser.Parser;
import ru.job4j.grabber.repository.Store;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Выполнение задач по расписанию.
 */
public class SchedulerManager {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerManager.class);

    /**
     * Объект для управления расписанием задач
     */
    private Scheduler scheduler;

    /**
     * Инициализирует планировщик.
     */
    public void init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            LOG.error("When init scheduler", e);
        }
    }

    /**
     * Создает и запускает задачу с указанным интервалом.
     *
     * @param period интервал в секундах между запусками задачи
     * @param task   класс задачи, которая будет выполняться
     * @param store  хранилище для сохранения результатов выполнения задачи
     * @param parser parser
     */
    public void load(int period, Class<SuperJobGrab> task, Store store, Parser parser) {
        try {
            var data = new JobDataMap();
            data.put("store", store);
            data.put("parser", parser);
            var job = newJob(task)
                    .usingJobData(data)
                    .build();

            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(period)
                    .repeatForever();

            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOG.error("When init job", e);
        }
    }

    /**
     * Завершает работу планировщика.
     */
    public void close() {
        if (scheduler != null) {
            try {
                scheduler.shutdown();
            } catch (SchedulerException e) {
                LOG.error("When shutdown scheduler", e);
            }
        }
    }
}
