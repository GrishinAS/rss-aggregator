package com.tochka.aggregator.service;

import com.tochka.aggregator.model.ParsingRequest;

import java.util.List;

public interface FeedParser {
  List parse(ParsingRequest request);
}
