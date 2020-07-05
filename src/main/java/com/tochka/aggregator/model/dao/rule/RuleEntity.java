package com.tochka.aggregator.model.dao.rule;

import com.tochka.aggregator.model.FeedType;
import com.tochka.aggregator.model.Rule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The class for saving rules to db
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = RuleEntity.TABLE_NAME)
public class RuleEntity {
  public static final String TABLE_NAME = "rules";

  @Id
  @GeneratedValue
  private int id;

  FeedType type;
  String title;
  String item;
  String link;
  String pubDate;
  String pubDateFormat; //format in which date must be parsed. Example "dd-MM-yy"
  String description;

  public static RuleEntity fromRule(Rule rule) {
    return new RuleEntity(
      0,
      rule.getType(),
      rule.getTitle(),
      rule.getItem(),
      rule.getLink(),
      rule.getPubDate(),
      rule.getPubDateFormat(),
      rule.getDescription()
    );
  }
}
