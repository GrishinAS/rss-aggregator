package com.tochka.aggregator.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Slf4j
public class FeedRefresherJobFactory implements JobFactory, ApplicationContextAware {

  private Scheduler scheduler;
  private String GROUP_NAME = "Default";
  private int REPEAT_TIMER = 10;
  private ApplicationContext context;


  public FeedRefresherJobFactory() {
    initiateQuartzService();
  }

  private void initiateQuartzService() {
    try {
      scheduler = StdSchedulerFactory.getDefaultScheduler();
      scheduler.start();
      JobDetail job = JobBuilder.newJob(FeedRefresher.class)
        .withIdentity("FeedRefreshingJob", GROUP_NAME)
        .build();
      SimpleTrigger trigger = TriggerBuilder.newTrigger()
        .withIdentity("fireEvery10min", GROUP_NAME)
        .startNow()
        .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(REPEAT_TIMER))
        .forJob(job)
        .build();
      scheduler.setJobFactory(this);
      scheduler.scheduleJob(job, trigger);
    } catch (SchedulerException e) {
      log.error("Exception while scheduler initialization", e);
    }
  }

  @Override
  public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
    return context.getBean(bundle.getJobDetail().getJobClass());
  }

  @PreDestroy
  private void serviceShutdown() {
    try {
      scheduler.shutdown();
    } catch (SchedulerException e) {
      log.error("Exception in scheduler service shutdown");
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }
}
