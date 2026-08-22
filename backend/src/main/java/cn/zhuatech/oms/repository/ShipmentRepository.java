/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findAllByOrderByIdDesc();
    long countByStatusIn(Collection<Shipment.Status> statuses);
    long countByStatus(Shipment.Status status);
}
