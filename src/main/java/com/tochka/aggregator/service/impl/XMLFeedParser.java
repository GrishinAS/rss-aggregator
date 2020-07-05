package com.tochka.aggregator.service.impl;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.items.FeedItem;
import com.tochka.aggregator.service.FeedParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XMLFeedParser implements FeedParser {

  @Override
  public List parse(ParsingRequest request) {
    try {
      URL feedSource = new URL(request.getAddress()); //http://rssblog.whatisrss.com/feed/ https://news.yandex.ru/cyber_sport.rss
      SyndFeedInput input = new SyndFeedInput();
      SyndFeed feed = input.build(new XmlReader(feedSource));
      List<SyndEntryImpl> entries = feed.getEntries();
      return entries.stream()
        .map(entry -> FeedItem.builder()
          .title(entry.getTitle())
          .description(entry.getDescription().getValue())
          .pubDate(getPubDate(entry))
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

  private Timestamp getPubDate(SyndEntryImpl entry) {
    Date publishedDate = entry.getPublishedDate();
    long dateToSave;
    if (publishedDate != null)
      dateToSave = publishedDate.getTime();
    else
      dateToSave = new Date().getTime();
    return new Timestamp(dateToSave);
  }
}

