package com.tochka.aggregator.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = RssItem.TABLE_NAME)
@Entity
public class RssItem {
  public static final String TABLE_NAME = "rss_feed";

  @Id
  @GeneratedValue
  private int id;
  @Size(min = 1)
  private String title;
  @Size(min = 1)
  private String link;
  @Size(min = 1)
  @Column(name = "description", length = 65535, columnDefinition = "TEXT")
  @Type(type = "text")
  private String description;

  private Timestamp pubDate;
}
