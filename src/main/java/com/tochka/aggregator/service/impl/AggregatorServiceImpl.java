package com.tochka.aggregator.service.impl;

import com.tochka.aggregator.model.FeedType;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.service.AggregatorService;
import com.tochka.aggregator.service.FeedParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AggregatorServiceImpl implements AggregatorService {

  //  @Autowired TODO
//  @Qualifier
  private FeedParser httpFeedParser = new HTTPFeedParser();
  private FeedParser xmlFeedParser = new XMLFeedParser();

  @Override
  public List getAggregatedData(ParsingRequest request) {
    if (request.getRule() != null &&
      request.getRule().getType() != null &&
      !request.getRule().getType().equals(FeedType.RSS)) {
      return httpFeedParser.parse(request);
    } else
      return xmlFeedParser.parse(request);
  }
}
