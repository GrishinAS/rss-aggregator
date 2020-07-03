package com.tochka.aggregator.rest;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.RssCrudService;
import com.tochka.aggregator.model.dao.RssItem;
import com.tochka.aggregator.service.AggregatorService;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@RestController
public class AggregatorController {

  private final AggregatorService aggregatorService;
  private final RssCrudService rssCrudService;

  @Autowired
  public AggregatorController(AggregatorService aggregatorService, RssCrudService rssCrudService) {
    this.aggregatorService = aggregatorService;
    this.rssCrudService = rssCrudService;
  }

  @PostMapping("/parseAddress")
  public String parseAddress(@RequestBody ParsingRequest request) {
    List<Integer> createdRows = new ArrayList<>();
    List<RssItem> aggregatedData = aggregatorService.getAggregatedData(request);
    for (RssItem item : aggregatedData) {
      createdRows.add(rssCrudService.create(item));
    }
    return createdRows.size() + " rows was successfully added to DB";
  }

  JobDetail job = newJob(RomeJob.class).withIdentity("Id1", "Rome").build();
  SimpleTrigger trigger = newTrigger()
    .withIdentity("mytrigger", "group1")
    .startNow()
    .withSchedule(simpleSchedule()
      .withIntervalInMinutes(5)
      .repeatForever())
    .build();
}
