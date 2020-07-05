package com.tochka.aggregator.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * The class contains description of tags that must be parsed in order to gain specific part of news.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Rule {
  FeedType type;
  String title;
  String item;
  String link;
  String pubDate;
  String pubDateFormat; //format i which date must be parsed. Example "dd-MM-yy"
  String description;
}
