/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "oms_order_event")
public class OrderEvent extends BaseEntity {
    @Column(nullable = false, length = 40)
    private String orderNo;
    @Column(nullable = false, length = 30)
    private String eventType;
    @Column(nullable = false, length = 80)
    private String title;
    @Column(nullable = false, length = 255)
    private String description;
    @Column(nullable = false, length = 80)
    private String operatorName;
    @Column(nullable = false)
    private LocalDateTime occurredAt;

    protected OrderEvent() {}
    public OrderEvent(String orderNo, String eventType, String title, String description,
                      String operatorName, LocalDateTime occurredAt) {
        this.orderNo = orderNo;
        this.eventType = eventType;
        this.title = title;
        this.description = description;
        this.operatorName = operatorName;
        this.occurredAt = occurredAt;
    }

    public String getOrderNo() { return orderNo; }
    public String getEventType() { return eventType; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getOperatorName() { return operatorName; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}
