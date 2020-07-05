package com.tochka.aggregator.util;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.model.dao.feed.Feed;
import com.tochka.aggregator.model.dao.feed.FeedCrudService;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import com.tochka.aggregator.model.dao.items.FeedItemsCrudService;
import com.tochka.aggregator.service.AggregatorService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
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
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Collection<Feed> feeds = feedCrudService.readAll();
        for (Feed feedList : feeds) {
          ParsingRequest parsingRequest = new ParsingRequest(feedList.getAddress(), Rule.fromEntity(feedList.getRule()));
          List<FeedItemEntity> dataFromDb = feedItemsCrudService.findByFeedId(feedList.getId());
          List<FeedItemEntity> newParsedData = aggregatorService.getAggregatedData(parsingRequest);
            //feed items compare to new data
          for (FeedItemEntity newItem : newParsedData) {
                if(!dataFromDb.contains(newItem)){
                    feedItemsCrudService.create(newItem);
                }
            }
        }
    }
}
