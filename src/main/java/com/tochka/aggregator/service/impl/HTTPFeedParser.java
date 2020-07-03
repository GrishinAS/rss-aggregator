package com.tochka.aggregator.service.impl;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.model.dao.RssItem;
import com.tochka.aggregator.service.FeedParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class HTTPFeedParser implements FeedParser {

  @Override
  public List parse(ParsingRequest request) {
    try {
      // https://www.newsru.com
      Document doc = Jsoup.connect(request.getAddress()).get();
      List<RssItem> resultFeed = new ArrayList<>();
      Rule rule = request.getRule();
      Elements items = doc.getElementsByClass(rule.getItem());
      for (Element item : items) {
        RssItem rssItem = new RssItem();
        rssItem.setDescription(fillData(rule.getDescription(), item));
        rssItem.setTitle(fillData(rule.getTitle(), item));
        rssItem.setLink(fillData(rule.getLink(), item));
        rssItem.setPubDate(parseTimestamp(fillData(rule.getPubDate(), item)));
        resultFeed.add(rssItem);
      }
      return resultFeed;
    } catch (IOException e) {
      log.error("Error while parsing website", e);
      throw new RestClientException("Error while parsing website", e);
    } catch (ParseException e) {
      log.error("Error while parsing public date", e);
      throw new RestClientException("Error while parsing public date", e);
    }
  }

  private Timestamp parseTimestamp(String fillData) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy"); //TODO add to rule
    if (fillData == null)
      return null;
    Date parsedDate = sdf.parse(fillData);
    return new Timestamp(parsedDate.getTime());
  }

  private String fillData(String classToFind, Element item) {
    Elements elementsByClass = item.getElementsByClass(classToFind);
    String text = null;
    if (elementsByClass.size() > 0) {
      text = elementsByClass.get(0).text();
    }
    return text;
  }
}
