package com.tochka.aggregator.rest;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.feed.Feed;
import com.tochka.aggregator.model.dao.feed.FeedCrudService;
import com.tochka.aggregator.model.dao.items.FeedItemsCrudService;
import com.tochka.aggregator.model.dao.items.FeedItem;
import com.tochka.aggregator.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class AggregatorController {

  private final AggregatorService aggregatorService;
  private final FeedItemsCrudService feedItemsCrudService;
  private final FeedCrudService feedCrudService;

  @Autowired
  public AggregatorController(AggregatorService aggregatorService, FeedItemsCrudService feedItemsCrudService, FeedCrudService feedCrudService) {
    this.aggregatorService = aggregatorService;
    this.feedItemsCrudService = feedItemsCrudService;
    this.feedCrudService = feedCrudService;
  }

  @PostMapping("/parseAddress")
  public String parseAddress(@RequestBody ParsingRequest request) {
    List<Integer> createdRows = new ArrayList<>();
    List<FeedItem> aggregatedData = aggregatorService.getAggregatedData(request);
    int feedId = feedCrudService.create(Feed.builder()
            .address(request.getAddress())
            .rule(request.getRule()).build());
    for (FeedItem item : aggregatedData) {
      item.setFeedId(feedId);
      createdRows.add(feedItemsCrudService.create(item)); //think я тут добавляю элементы которые должны добавиться автоматом
    }

    return createdRows.size() + " rows was successfully added to DB";
  }

  @GetMapping("/feed")
  public Collection<FeedItem> getFeed(){
    return feedItemsCrudService.readAll();
  }

  @GetMapping("/feed")
  public FeedItem getFeed(@PathVariable String title){
    return feedItemsCrudService.findByTitle(title);
  }
}
