package com.tochka.aggregator.service.impl;

import com.tochka.aggregator.model.FeedType;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class XMLFeedParserTest {
  private XMLFeedParser parser = new XMLFeedParser();


  @Test
  public void parse() {
    Rule rule = new Rule(FeedType.RSS, "", "", "", "", "", "");
    ParsingRequest parsingRequest = new ParsingRequest("http://rssblog.whatisrss.com/feed/", rule);
    List<FeedItemEntity> parse = parser.parse(parsingRequest);
    Assert.assertTrue(parse.size() > 0);
  }
}