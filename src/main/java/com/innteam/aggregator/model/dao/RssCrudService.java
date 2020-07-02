package com.innteam.aggregator.model.dao;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class RssCrudService {
  private final RssRepository repo;

  @Autowired
  public RssCrudService(RssRepository repo) {
    this.repo = repo;
  }

  public int create(RssItem rss) {
    return this.repo.save(rss).getId();
  }

  @Transactional
  public RssItem read(int id) {
    return this.repo.findById(id).orElse(null);
  }

//  @Transactional
//  public Collection<RssItem> readAll(int pages, int count) {
//    return IterableUtils.toList(this.repo.findAll(PageRequest.of(pages, count, Sort.by(Sort.Direction.DESC, "pubDate"))));
//  }

  @Transactional
  public Collection<RssItem> readAll() {
    return IterableUtils.toList(this.repo.findAll());
  }

  @Transactional
  public RssItem update(RssItem object) {
    return this.repo.save(object);
  }

  @Transactional
  public void delete(int id) {
    this.repo.deleteById(id);
  }
}
