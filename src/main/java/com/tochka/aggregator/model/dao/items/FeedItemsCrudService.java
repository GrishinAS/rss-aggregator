package com.tochka.aggregator.model.dao.items;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Service
public class FeedItemsCrudService {
  private final FeedItemsRepository repo;
  @PersistenceContext
  private EntityManager entityManger;

  @Autowired
  public FeedItemsCrudService(FeedItemsRepository repo) {
    this.repo = repo;
  }

  public int create(FeedItem rss) {
    return this.repo.save(rss).getId();
  }

  @Transactional
  public FeedItem read(int id) {
    return this.repo.findById(id).orElse(null);
  }


  @Transactional
  public Collection<FeedItem> readAll() {
    return IterableUtils.toList(this.repo.findAll());
  }

  @Transactional
  public FeedItem update(FeedItem object) {
    return this.repo.save(object);
  }

  @Transactional
  public void delete(int id) {
    this.repo.deleteById(id);
  }

  @Transactional
  public FeedItem findByTitle(String title) {
    Query query = entityManger.createQuery("select f from feed_items f where title like :title", FeedItem.class);
    query.setParameter("title", title);
    return (FeedItem) query.getSingleResult();
  }

  @Transactional
  public List<FeedItem> findByFeedId(int id) {
    Query query = entityManger.createQuery("select f from feed_items f where feed_id=:id", FeedItem.class);
    query.setParameter("id", id);
    return query.getResultList();
  }
}
