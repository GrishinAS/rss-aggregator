package com.tochka.aggregator.service.impl;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.tochka.aggregator.model.FeedType;
import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.model.dao.RssItem;
import com.tochka.aggregator.service.AggregatorService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AggregatorServiceImpl implements AggregatorService {

  @Override
  public List getAggregatedData(ParsingRequest request) {
    if (request.getRule() != null &&
      request.getRule().getType() != null &&
      !request.getRule().getType().equals(FeedType.RSS)) {
      return getNonRssFeed(request);
    } else return getRssFeed(request);
  }

  private List getNonRssFeed(ParsingRequest request) {
    try {
      // https://www.newsru.com
      Document doc = Jsoup.connect(request.getAddress()).get();
      List<RssItem> resultFeed = new ArrayList<>();
      Rule rule = request.getRule();
      Elements items = doc.select(rule.getItem());
      for (Element item : items) {
        RssItem rssItem = new RssItem();
        rssItem.setDescription(item.getElementsByClass(rule.getDescription()).get(0).text());
        rssItem.setTitle(item.getElementsByClass(rule.getTitle()).get(0).text());
        rssItem.setLink(item.getElementsByClass(rule.getLink()).get(0).text());
        //rssItem.setPubDate(item.getElementsByClass(rule.getPubDate()).get(0).text());
        resultFeed.add(rssItem);
      }
      return resultFeed;
    } catch (IOException e) {
      log.error("Error while parsing website", e);
      throw new RestClientException("Error while parsing website", e);
    }
  }


  private List getRssFeed(ParsingRequest request) {
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
