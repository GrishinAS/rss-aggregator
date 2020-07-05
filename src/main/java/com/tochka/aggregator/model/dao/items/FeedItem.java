package com.tochka.aggregator.model.dao.items;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = FeedItem.TABLE_NAME)
@Entity
public class FeedItem {
  public static final String TABLE_NAME = "feed_items";

  @Id
  @GeneratedValue
  private int id;
  @JoinColumn //TODO finish
  @Setter
  private int feedId;
  @Size(min = 1)
  private String title;
  @Size(min = 1)
  private String link;
  @Size(min = 1)
  @Column(name = "description", length = 65535, columnDefinition = "TEXT")
  @Type(type = "text")
  private String description;

  private Timestamp pubDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FeedItem feedItem = (FeedItem) o;
    return title.equals(feedItem.title) &&
            Objects.equals(link, feedItem.link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, link);
  }
}
