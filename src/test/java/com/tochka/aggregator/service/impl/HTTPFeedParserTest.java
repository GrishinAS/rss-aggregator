package com.tochka.aggregator.service.impl;

import com.tochka.aggregator.model.FeedType;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HTTPFeedParserTest {
  private HTTPFeedParser parser = new HTTPFeedParser();


  @Test
  public void parse() {
    Rule rule = new Rule(FeedType.WEBSITE, "left-feed-item", "left-feed-title", "inside", "", "", "left-feed-anons");
    ParsingRequest parsingRequest = new ParsingRequest("https://www.newsru.com", rule);
    List<FeedItemEntity> parse = parser.parse(parsingRequest);
    Assert.assertTrue(parse.size() > 0);
  }
}