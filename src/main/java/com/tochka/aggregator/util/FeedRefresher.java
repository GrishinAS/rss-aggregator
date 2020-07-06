package com.tochka.aggregator.util;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.model.dao.feed.Feed;
import com.tochka.aggregator.model.dao.feed.FeedCrudService;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import com.tochka.aggregator.model.dao.items.FeedItemsCrudService;
import com.tochka.aggregator.service.AggregatorService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Service for refreshing entire feed from db every 10 minutes
 */
@Service
@Slf4j
public class FeedRefresher implements Job {

  private final FeedItemsCrudService feedItemsCrudService;
  private final FeedCrudService feedCrudService;
  private final AggregatorService aggregatorService;

  @Autowired
  public FeedRefresher(FeedItemsCrudService feedItemsCrudService, FeedCrudService feedCrudService, AggregatorService aggregatorService) {
    this.feedItemsCrudService = feedItemsCrudService;
    this.feedCrudService = feedCrudService;
    this.aggregatorService = aggregatorService;
  }

  @Override
  @Transactional
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    log.info("Scheduled refresh executed");
    Collection<Feed> feeds = feedCrudService.readAll();
    for (Feed feedList : feeds) {
      ParsingRequest parsingRequest = new ParsingRequest(feedList.getAddress(), Rule.fromEntity(feedList.getRule()));
      List<FeedItemEntity> dataFromDb = feedList.getFeeds();
      //List<FeedItemEntity> dataFromDb = feedItemsCrudService.findByFeedId(feedList.getId());
      List<FeedItemEntity> newParsedData = aggregatorService.getAggregatedData(parsingRequest);
      //feed items compare to new data
      int newItems = 0;
      for (FeedItemEntity newItem : newParsedData) {
        if(!dataFromDb.contains(newItem)){
          feedItemsCrudService.create(newItem);
          newItems++;
        }
      }
      log.info(newItems + " new feed items added to database");
    }
    log.info("Scheduled refresh finished");
  }
}
