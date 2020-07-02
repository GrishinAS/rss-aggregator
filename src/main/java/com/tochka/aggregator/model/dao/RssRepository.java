package com.tochka.aggregator.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RssRepository extends JpaRepository<RssItem, Integer> {
}
