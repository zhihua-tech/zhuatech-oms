/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<Shipment> findAllByOrderByIdDesc();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByStatusIn(Collection<Shipment.Status> statuses);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByStatus(Shipment.Status status);
}
