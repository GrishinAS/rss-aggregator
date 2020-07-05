package com.tochka.aggregator.service.impl;

import com.tochka.aggregator.model.FeedType;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import com.tochka.aggregator.service.AggregatorService;
import com.tochka.aggregator.service.FeedParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AggregatorServiceImpl implements AggregatorService {

  private final FeedParser httpFeedParser;
  private final FeedParser xmlFeedParser;

  @Autowired
  public AggregatorServiceImpl(@Qualifier("HTTPFeedParser") FeedParser httpFeedParser,
                               @Qualifier("XMLFeedParser") FeedParser xmlFeedParser) {
    this.httpFeedParser = httpFeedParser;
    this.xmlFeedParser = xmlFeedParser;
  }

  @Override
  public List<FeedItemEntity> getAggregatedData(ParsingRequest request) {
    if (request.getRule() != null &&
      request.getRule().getType() != null &&
      !request.getRule().getType().equals(FeedType.RSS)) {
      return httpFeedParser.parse(request);
    } else
      return xmlFeedParser.parse(request);
  }
}
