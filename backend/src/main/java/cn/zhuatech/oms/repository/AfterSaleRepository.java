/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.AfterSaleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface AfterSaleRepository extends JpaRepository<AfterSaleRequest, Long> {
    boolean existsByRequestNo(String requestNo);
    List<AfterSaleRequest> findAllByOrderByRequestedAtDesc();
    long countByStatusIn(Collection<AfterSaleRequest.Status> statuses);
}
