package com.innteam.aggregator.model;

import lombok.Data;

@Data
public class RssItem {
  private String title;
  private String link;
  private String guid;
  private String description;
  private String pubDate;
}
