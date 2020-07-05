package com.tochka.aggregator.service;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;

import java.util.List;

public interface FeedParser {
  List<FeedItemEntity> parse(ParsingRequest request);
}
