package com.tochka.aggregator.service.impl;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import org.junit.Assert;
import org.junit.Before;

import java.io.IOException;
import java.util.List;

public class AggregatorServiceImplTest {
  private AggregatorServiceImpl aggregatorService;

  @Before
  public void init() {
    aggregatorService = new AggregatorServiceImpl();
  }

  @org.junit.Test
  public void getAggregatedData() throws IOException {

    ParsingRequest request = new ParsingRequest("http://rssblog.whatisrss.com/feed/", new Rule());
    List aggregatedData = aggregatorService.getAggregatedData(request);
    Assert.assertTrue(aggregatedData.size() > 0);
  }
}