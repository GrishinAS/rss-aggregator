package com.innteam.aggregator.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

public class AggregatorServiceImplTest {
  private AggregatorServiceImpl aggregatorService;

  @Before
  public void init() {

  }

  @org.junit.Test
  public void getAggregatedData() throws IOException {

    List aggregatedData = aggregatorService.getAggregatedData();
    Assert.assertTrue(aggregatedData.size() > 0);
  }
}