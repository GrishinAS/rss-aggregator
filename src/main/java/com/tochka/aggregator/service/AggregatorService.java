package com.tochka.aggregator.service;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;

import java.util.List;

public interface AggregatorService {
  List<FeedItemEntity> getAggregatedData(ParsingRequest request);
}
