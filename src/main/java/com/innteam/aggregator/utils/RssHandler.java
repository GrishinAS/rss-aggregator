package com.innteam.aggregator.utils;

import com.innteam.aggregator.model.RssItem;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class RssHandler extends DefaultHandler {

  private Map<String, Boolean> tagElements = new HashMap<>();
  private StringBuilder data = null;
  private RssItem item = null;
  private List<RssItem> parsedItems = new ArrayList<>();

  {
    tagElements.put("item", false);
    tagElements.put("title", false);
    tagElements.put("link", false);
    tagElements.put("description", false);
    tagElements.put("pubDate", false);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equals("item")) item = new RssItem();
    tagElements.replace(qName, true);
    data = new StringBuilder();
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    data.append(new String(ch, start, length));
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    tagElements.forEach((tag, isThisTagProcessingNow) -> {
      if (isThisTagProcessingNow) {
        if (tag.equals("description")) item.setDescription(data.toString()); //непонятно какое поле заполнять. рефлексия?
        if (tag.equals("link")) item.setLink(data.toString());
        if (tag.equals("pubDate")) item.setPubDate(data.toString());
        if (tag.equals("title")) item.setTitle(data.toString());
        if (tag.equals("item")) parsedItems.add(item);
      }
    });
    tagElements.replace(qName, false);
  }
}
