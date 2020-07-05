package com.tochka.aggregator.service;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.items.FeedItem;

import java.util.List;

public interface FeedParser {
  List<FeedItem> parse(ParsingRequest request);
}
