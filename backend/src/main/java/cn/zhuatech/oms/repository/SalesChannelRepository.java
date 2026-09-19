/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.repository;

import cn.zhuatech.oms.model.SalesChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface SalesChannelRepository extends JpaRepository<SalesChannel, Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<SalesChannel> findAllByOrderByTodayAmountDesc();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countBySyncStatusNot(SalesChannel.SyncStatus status);
}
