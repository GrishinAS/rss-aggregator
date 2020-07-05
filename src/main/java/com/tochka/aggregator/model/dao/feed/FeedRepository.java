package com.tochka.aggregator.model.dao.feed;

import com.tochka.aggregator.model.dao.items.FeedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Integer> {
}
