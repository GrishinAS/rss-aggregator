package com.tochka.aggregator.model;

import com.tochka.aggregator.model.dao.rule.RuleEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * The class contains description of tags that must be parsed in order to gain specific part of news.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rule {
  FeedType type;
  String title;
  String item;
  String link;
  String pubDate;
  String pubDateFormat; //format i which date must be parsed. Example "dd-MM-yy"
  String description;

  public static Rule fromEntity(RuleEntity rule) {
    return new Rule(
      rule.getType(),
      rule.getTitle(),
      rule.getItem(),
      rule.getLink(),
      rule.getLink(),
      rule.getPubDateFormat(),
      rule.getDescription()
    );
  }
}
