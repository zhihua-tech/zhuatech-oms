/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.CustomerOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    boolean existsByOrderNo(String orderNo);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<CustomerOrder> findAllByOrderByOrderedAtDesc();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByStatusIn(Collection<CustomerOrder.Status> statuses);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByOrderedAtBetween(LocalDateTime start, LocalDateTime end);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Query("select coalesce(sum(o.totalAmount), 0) from CustomerOrder o where o.orderedAt >= :start and o.orderedAt < :end")
    BigDecimal sumBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
