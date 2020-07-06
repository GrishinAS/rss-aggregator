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

  public int create(FeedItemEntity rss) {
    return this.repo.save(rss).getId();
  }

  @Transactional
  public FeedItemEntity read(int id) {
    return this.repo.findById(id).orElse(null);
  }


  @Transactional
  public Collection<FeedItemEntity> readAll() {
    return IterableUtils.toList(this.repo.findAll());
  }

  @Transactional
  public FeedItemEntity update(FeedItemEntity object) {
    return this.repo.save(object);
  }

  @Transactional
  public void delete(int id) {
    this.repo.deleteById(id);
  }

  @Transactional
  public FeedItemEntity findByTitle(String title) {
    Query query = entityManger.createQuery("select f from FeedItemEntity f where title like :title", FeedItemEntity.class);
    query.setParameter("title", "%" + title + "%");
    return (FeedItemEntity) query.getSingleResult();
  }

  @Transactional
  public List<FeedItemEntity> findByFeedId(int id) {
    Query query = entityManger.createQuery("select f from FeedItemEntity f where feed_id=:id", FeedItemEntity.class);
    query.setParameter("id", id);
    return query.getResultList();
  }
}
