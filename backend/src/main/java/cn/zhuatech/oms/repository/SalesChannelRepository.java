/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.SalesChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalesChannelRepository extends JpaRepository<SalesChannel, Long> {
    List<SalesChannel> findAllByOrderByTodayAmountDesc();
    long countBySyncStatusNot(SalesChannel.SyncStatus status);
}
