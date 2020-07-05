package com.tochka.aggregator.model.dao.items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedItemsRepository extends JpaRepository<FeedItem, Integer> {
}
