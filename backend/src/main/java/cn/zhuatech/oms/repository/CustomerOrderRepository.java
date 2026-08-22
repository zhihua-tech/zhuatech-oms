/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.CustomerOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    boolean existsByOrderNo(String orderNo);
    List<CustomerOrder> findAllByOrderByOrderedAtDesc();
    long countByStatusIn(Collection<CustomerOrder.Status> statuses);
    long countByOrderedAtBetween(LocalDateTime start, LocalDateTime end);
    @Query("select coalesce(sum(o.totalAmount), 0) from CustomerOrder o where o.orderedAt >= :start and o.orderedAt < :end")
    BigDecimal sumBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
