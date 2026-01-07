package com.example.lp.order.repository;

import com.example.lp.member.entity.Member;
import com.example.lp.order.entity.Order;
import com.example.lp.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByState(String state);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderDetailList "+
            "WHERE o.member = :member AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findOrderAndOrderDetailByMemberAndDate(@Param("member") Member member,
                                                       @Param("startDate") OffsetDateTime startDate,
                                                       @Param("endDate") OffsetDateTime endDate);
}
