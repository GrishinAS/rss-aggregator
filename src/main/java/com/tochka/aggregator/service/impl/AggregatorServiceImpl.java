package com.tochka.aggregator.service.impl;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.RssItem;
import com.tochka.aggregator.service.AggregatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AggregatorServiceImpl implements AggregatorService {


  @Autowired
  public AggregatorServiceImpl() {

  }

  @Override
  public List getAggregatedData(ParsingRequest request) {
    try {
      URL feedSource = new URL(request.getAddress()); //http://rssblog.whatisrss.com/feed/ https://news.yandex.ru/cyber_sport.rss
      SyndFeedInput input = new SyndFeedInput();
      SyndFeed feed = input.build(new XmlReader(feedSource));
      List<SyndEntryImpl> entries = feed.getEntries();
      return entries.stream()
        .map(entry -> RssItem.builder()
          .title(entry.getTitle())
          .description(entry.getDescription().getValue())
          .pubDate(new Timestamp(entry.getPublishedDate().getTime()))
          .link(entry.getLink())
          .build())
        .collect(Collectors.toList());
    } catch (MalformedURLException e) {
      log.error("Wrong url specified" + request.getAddress(), e);
      throw new RestClientException("Wrong url specified" + request.getAddress(), e);
    } catch (IOException | FeedException e) {
      log.error("Error while parsing rss xml", e);
      throw new RestClientException("Error while parsing rss xml", e);
    }
  }
}
