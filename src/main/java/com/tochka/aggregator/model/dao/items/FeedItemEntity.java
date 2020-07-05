package com.tochka.aggregator.model.dao.items;

import com.tochka.aggregator.model.dao.feed.Feed;
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
@Table(name = FeedItemEntity.TABLE_NAME)
@Entity
public class FeedItemEntity {
  public static final String TABLE_NAME = "feed_items";

  @Id
  @GeneratedValue
  private int id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feed_id")
  @Setter
  private Feed feed;
  @Size(min = 1)
  private String title;

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
    FeedItemEntity feedItemEntity = (FeedItemEntity) o;
    return title.equals(feedItemEntity.title) &&
      Objects.equals(link, feedItemEntity.link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, link);
  }
}
