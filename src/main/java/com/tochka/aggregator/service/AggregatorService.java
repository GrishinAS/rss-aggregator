package com.tochka.aggregator.service;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.items.FeedItem;

import java.util.List;

public interface AggregatorService {
  List<FeedItem> getAggregatedData(ParsingRequest request);
}
