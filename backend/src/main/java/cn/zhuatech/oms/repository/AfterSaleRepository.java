/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.AfterSaleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface AfterSaleRepository extends JpaRepository<AfterSaleRequest, Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    boolean existsByRequestNo(String requestNo);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<AfterSaleRequest> findAllByOrderByRequestedAtDesc();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByStatusIn(Collection<AfterSaleRequest.Status> statuses);
}
