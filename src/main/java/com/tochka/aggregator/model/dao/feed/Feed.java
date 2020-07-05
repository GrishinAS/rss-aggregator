package com.tochka.aggregator.model.dao.feed;

import com.tochka.aggregator.model.dao.items.FeedItemEntity;
import com.tochka.aggregator.model.dao.rule.RuleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Feed.TABLE_NAME)
@Entity
public class Feed {
  public static final String TABLE_NAME = "feed";

  @Id
  @GeneratedValue
  @Column(name = "feed_id")
  private int id;
  @Size(min = 1)
  private String address;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "feed")
  private List<FeedItemEntity> feeds;

  @OneToOne(cascade = CascadeType.ALL)
  private RuleEntity rule;
}
