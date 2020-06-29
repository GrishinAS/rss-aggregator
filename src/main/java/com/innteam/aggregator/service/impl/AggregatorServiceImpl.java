package com.innteam.aggregator.service.impl;

import com.innteam.aggregator.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AggregatorServiceImpl implements AggregatorService {

  private ExecutorService dataCollectors = Executors.newCachedThreadPool();

  @Autowired
  public AggregatorServiceImpl( ) {

  }

  @Override
  public List getAggregatedData() {
   return null;
  }
}
