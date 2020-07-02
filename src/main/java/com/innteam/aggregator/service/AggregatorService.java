package com.innteam.aggregator.service;

import com.innteam.aggregator.model.ParsingRequest;

import java.util.List;

public interface AggregatorService {
  List getAggregatedData(ParsingRequest request);
}
