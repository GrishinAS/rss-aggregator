package com.tochka.aggregator.model.dao.feed;

import com.tochka.aggregator.model.Rule;
import com.tochka.aggregator.model.dao.items.FeedItem;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = FeedItem.TABLE_NAME)
@Entity
public class Feed {
  public static final String TABLE_NAME = "feed";

  @Id
  @GeneratedValue
  private int id;
  @Size(min = 1)
  private String address;

  private Rule rule;
}
