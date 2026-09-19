/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity
@Table(name = "oms_sales_channel")
public class SalesChannel extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Type { MARKETPLACE, SELF_OPERATED, OFFLINE, DISTRIBUTOR }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { ENABLED, DISABLED }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum SyncStatus { NORMAL, WARNING, OFFLINE }

    @Column(nullable = false, unique = true, length = 30)
    private String code;
    @Column(nullable = false, length = 80)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private Type type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SyncStatus syncStatus;
    @Column(nullable = false)
    private long todayOrders;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal todayAmount;
    private LocalDateTime lastSyncAt;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected SalesChannel() {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public SalesChannel(String code, String name, Type type, Status status, SyncStatus syncStatus,
                        long todayOrders, BigDecimal todayAmount, LocalDateTime lastSyncAt) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.status = status;
        this.syncStatus = syncStatus;
        this.todayOrders = todayOrders;
        this.todayAmount = todayAmount;
        this.lastSyncAt = lastSyncAt;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCode() { return code; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getName() { return name; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Type getType() { return type; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public SyncStatus getSyncStatus() { return syncStatus; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public long getTodayOrders() { return todayOrders; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getTodayAmount() { return todayAmount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getLastSyncAt() { return lastSyncAt; }
}
