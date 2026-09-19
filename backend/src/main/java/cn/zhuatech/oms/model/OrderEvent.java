/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
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

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected OrderEvent() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public OrderEvent(String orderNo, String eventType, String title, String description,
                      String operatorName, LocalDateTime occurredAt) {
        this.orderNo = orderNo;
        this.eventType = eventType;
        this.title = title;
        this.description = description;
        this.operatorName = operatorName;
        this.occurredAt = occurredAt;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getOrderNo() { return orderNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getEventType() { return eventType; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getTitle() { return title; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getDescription() { return description; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getOperatorName() { return operatorName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getOccurredAt() { return occurredAt; }
}
