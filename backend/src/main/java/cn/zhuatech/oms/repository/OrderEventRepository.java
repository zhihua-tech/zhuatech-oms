/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
    List<OrderEvent> findTop20ByOrderByOccurredAtDesc();
}
