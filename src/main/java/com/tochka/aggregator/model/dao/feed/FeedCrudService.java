package com.tochka.aggregator.model.dao.feed;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class FeedCrudService {
  private final FeedRepository repo;

  @Autowired
  public FeedCrudService(FeedRepository repo) {
    this.repo = repo;
  }

  public int create(Feed rss) {
    return this.repo.save(rss).getId();
  }

  @Transactional
  public Feed read(int id) {
    return this.repo.findById(id).orElse(null);
  }


  @Transactional
  public Collection<Feed> readAll() {
    return IterableUtils.toList(this.repo.findAll());
  }

  @Transactional
  public Feed update(Feed object) {
    return this.repo.save(object);
  }

  @Transactional
  public void delete(int id) {
    this.repo.deleteById(id);
  }
}
