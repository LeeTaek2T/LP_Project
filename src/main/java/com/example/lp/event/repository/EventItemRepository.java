package com.example.lp.event.repository;

import com.example.lp.event.entity.EventItem;
import com.example.lp.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventItemRepository extends JpaRepository<EventItem, Long> {
//    @Query("""
//select ei
//from EventItem ei
//join fetch ei.event e
//join fetch ei.product p
//join fetch p.productSkuList s
//where e.id = :eventId
//""")
    List<EventItem> findByEventId(Long eventId);


    Optional<EventItem> findByProduct(Product product);
}
