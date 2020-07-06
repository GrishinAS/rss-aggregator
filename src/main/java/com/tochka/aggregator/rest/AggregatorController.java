package com.tochka.aggregator.rest;

import com.tochka.aggregator.model.FeedItem;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.feed.Feed;
import com.tochka.aggregator.model.dao.feed.FeedCrudService;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import com.tochka.aggregator.model.dao.items.FeedItemsCrudService;
import com.tochka.aggregator.model.dao.rule.RuleEntity;
import com.tochka.aggregator.service.AggregatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
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

  @PostMapping("/address")
  public String parseAddress(@RequestBody ParsingRequest request) {
    List<FeedItemEntity> aggregatedData = aggregatorService.getAggregatedData(request);

    Feed feed = Feed.builder()
      .address(request.getAddress())
      .feeds(aggregatedData)
      .rule(RuleEntity.fromRule(request.getRule())).build();

    for (FeedItemEntity item : aggregatedData) {
      item.setFeed(feed);
    }
    feedCrudService.create(feed);

    String message = aggregatedData.size() + " rows was successfully added to DB";
    log.info(message);
    return message;
  }

  @GetMapping("/feed")
  public Collection<FeedItem> getFeed(){
    Collection<FeedItemEntity> feedItemEntities = feedItemsCrudService.readAll();
    return feedItemEntities.stream()
      .map(dbItem -> FeedItem.builder()
        .description(dbItem.getDescription())
        .title(dbItem.getTitle())
        .link(dbItem.getLink())
        .pubDate(dbItem.getPubDate())
        .build())
      .collect(Collectors.toList());
  }

  @GetMapping("/feed/item")
  public FeedItem getFeed(@RequestParam String title) {
    FeedItemEntity foundEntity = feedItemsCrudService.findByTitle(title);
    return FeedItem.fromEntity(foundEntity);
  }
}
