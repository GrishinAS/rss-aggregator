package com.tochka.aggregator.service.impl;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.service.FeedParser;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AggregatorServiceImplTest {
  private AggregatorServiceImpl aggregatorService;
  @Mock
  private final FeedParser httpFeedParser = mock(FeedParser.class);
  @Mock
  private final FeedParser xmlFeedParser = mock(FeedParser.class);

  @Before
  public void init() {
    aggregatorService = new AggregatorServiceImpl(httpFeedParser, xmlFeedParser);
  }

  @org.junit.Test
  public void getAggregatedData() throws IOException {

    ParsingRequest request = new ParsingRequest("http://rssblog.whatisrss.com/feed/", new Rule());
    List aggregatedData = aggregatorService.getAggregatedData(request);
    Assert.assertTrue(aggregatedData.size() > 0);
  }
}