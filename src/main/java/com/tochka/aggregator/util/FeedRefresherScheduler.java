package com.tochka.aggregator.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class FeedRefresherScheduler {

    private void startJob(){
        try {
            SchedulerFactory schedFact = new StdSchedulerFactory();
            Scheduler scheduler = schedFact.getScheduler();
            scheduler.start();


            JobBuilder jobBuilder = JobBuilder.newJob(FeedRefresher.class);


            String GROUP_NAME = "group1";
            JobDetail jobDetail = jobBuilder
                    .withIdentity("feedRefreshScheduler", GROUP_NAME)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("fireEvery10min", GROUP_NAME)
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(10))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("All triggers executed. Shutdown scheduler");
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error("Scheduler error", e);
        }
    }
}
