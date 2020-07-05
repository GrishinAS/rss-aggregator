package com.tochka.aggregator.model;

import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class FeedItem {
  private String title;
  private String link;
  private String description;
  private Timestamp pubDate;

  public static FeedItem fromEntity(FeedItemEntity foundEntity) {
    return FeedItem.builder()
      .description(foundEntity.getDescription())
      .title(foundEntity.getTitle())
      .link(foundEntity.getLink())
      .pubDate(foundEntity.getPubDate())
      .build();
  }
}
