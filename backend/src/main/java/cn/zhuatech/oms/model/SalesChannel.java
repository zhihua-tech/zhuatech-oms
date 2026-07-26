/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "oms_sales_channel")
public class SalesChannel extends BaseEntity {
    public enum Type { MARKETPLACE, SELF_OPERATED, OFFLINE, DISTRIBUTOR }
    public enum Status { ENABLED, DISABLED }
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

    protected SalesChannel() {}

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

    public String getCode() { return code; }
    public String getName() { return name; }
    public Type getType() { return type; }
    public Status getStatus() { return status; }
    public SyncStatus getSyncStatus() { return syncStatus; }
    public long getTodayOrders() { return todayOrders; }
    public BigDecimal getTodayAmount() { return todayAmount; }
    public LocalDateTime getLastSyncAt() { return lastSyncAt; }
}
