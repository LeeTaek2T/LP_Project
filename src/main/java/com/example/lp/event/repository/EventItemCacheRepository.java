package com.example.lp.event.repository;

import com.example.lp.event.entity.EventItemCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventItemCacheRepository extends JpaRepository<EventItemCache, Long> {

    List<EventItemCache> findByEventId(Long eventId);

    List<EventItemCache> findByProductId(Long productId);
}
