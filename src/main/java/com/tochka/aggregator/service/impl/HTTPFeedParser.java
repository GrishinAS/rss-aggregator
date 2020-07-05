package com.tochka.aggregator.service.impl;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import com.tochka.aggregator.service.FeedParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class HTTPFeedParser implements FeedParser {

  @Override
  public List<FeedItemEntity> parse(ParsingRequest request) {
    try {
      // https://www.newsru.com
      Document doc = Jsoup.connect(request.getAddress()).get();
      List<FeedItemEntity> resultFeed = new ArrayList<>();
      Rule rule = request.getRule();
      Elements items = doc.getElementsByClass(rule.getItem());
      for (Element item : items) {
        FeedItemEntity feedItemEntity = FeedItemEntity.builder()
                .description(fillData(rule.getDescription(), item))
                .title(fillData(rule.getTitle(), item))
                .link(fillData(rule.getLink(), item))
                .pubDate(parseTimestamp(fillData(rule.getPubDate(), item), rule.getPubDateFormat()))
                .build();
        resultFeed.add(feedItemEntity);
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

  private Timestamp parseTimestamp(String fillData, String pubDateFormat) throws ParseException {
    if (fillData == null || fillData.isEmpty())
      return new Timestamp(new Date().getTime());
    if (pubDateFormat == null || pubDateFormat.isEmpty())
      throw new RestClientException("Publication date format is empty");
    SimpleDateFormat sdf = new SimpleDateFormat(pubDateFormat);
    Date parsedDate = sdf.parse(fillData);
    return new Timestamp(parsedDate.getTime());
  }

  private String fillData(String classToFind, Element item) {
    String text = "";
    if (classToFind == null || classToFind.isEmpty())
      return text;
    Elements elementsByClass = item.getElementsByClass(classToFind);
    if (elementsByClass.size() > 0) {
      text = elementsByClass.get(0).text();
    }
    return text;
  }
}
