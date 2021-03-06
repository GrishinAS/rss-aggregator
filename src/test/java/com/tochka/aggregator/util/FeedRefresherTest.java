package com.tochka.aggregator.util;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.feed.Feed;
import com.tochka.aggregator.model.dao.feed.FeedCrudService;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import com.tochka.aggregator.model.dao.items.FeedItemsCrudService;
import com.tochka.aggregator.model.dao.rule.RuleEntity;
import com.tochka.aggregator.service.AggregatorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeedRefresherTest {
  private FeedRefresher refresher;
  @Mock
  private final FeedItemsCrudService feedItemsCrudService = mock(FeedItemsCrudService.class);
  @Mock
  private final FeedCrudService feedCrudService = mock(FeedCrudService.class);
  @Mock
  private final AggregatorService aggregatorService = mock(AggregatorService.class);
  @Mock
  private final JobExecutionContext jobExecutionContext = mock(JobExecutionContext.class);


  @Before
  public void init() {
    Feed feed = new Feed(1, "", null, new RuleEntity());
    FeedItemEntity oldFeedItem = new FeedItemEntity(1, feed, "oldFeed", "oldFeed", "", new Timestamp(new Date().getTime()));
    FeedItemEntity oldFeedItem2 = new FeedItemEntity(2, feed, "oldFeed2", "oldFeed2", "", new Timestamp(new Date().getTime()));
    List<FeedItemEntity> oldFeed = new ArrayList<>();
    oldFeed.add(oldFeedItem);
    oldFeed.add(oldFeedItem2);
    feed.setFeeds(oldFeed);
    FeedItemEntity newFeedItem = new FeedItemEntity(3, feed, "newFeed", "newFeed", "", new Timestamp(new Date().getTime()));
    FeedItemEntity newFeedItem2 = new FeedItemEntity(4, feed, "newFeed2", "newFeed2", "", new Timestamp(new Date().getTime()));
    List<FeedItemEntity> newFeed = new ArrayList<>();
    newFeed.add(oldFeedItem);
    newFeed.add(newFeedItem);
    newFeed.add(newFeedItem2);

    when(feedCrudService.readAll())
      .thenReturn(Collections.singletonList(feed));
    doReturn(oldFeed)
      .when(feedItemsCrudService)
      .findByFeedId(anyInt());
    doReturn(newFeed)
      .when(aggregatorService)
      .getAggregatedData(any(ParsingRequest.class));
    refresher = new FeedRefresher(feedItemsCrudService, feedCrudService, aggregatorService);
  }

  @Test
  public void addingNewElements() throws JobExecutionException {
    refresher.execute(jobExecutionContext);
    verify(feedItemsCrudService, times(2)).create(any(FeedItemEntity.class));
  }

}