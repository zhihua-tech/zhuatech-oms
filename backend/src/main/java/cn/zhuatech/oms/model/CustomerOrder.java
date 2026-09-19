/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity
@Table(name = "oms_order")
public class CustomerOrder extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { PENDING_PAYMENT, PAID, ALLOCATING, READY_TO_SHIP, SHIPPED, COMPLETED, CANCELLED, AFTER_SALE }

    @Column(nullable = false, unique = true, length = 40)
    private String orderNo;
    @Column(length = 60)
    private String externalOrderNo;
    @Column(nullable = false, length = 80)
    private String channelName;
    @Column(nullable = false, length = 80)
    private String customerName;
    @Column(length = 30)
    private String customerPhone;
    @Column(nullable = false, length = 500)
    private String itemSummary;
    @Column(nullable = false)
    private int itemCount;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal totalAmount;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal paidAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private Status status;
    @Column(length = 80)
    private String warehouseName;
    @Column(length = 80)
    private String carrierName;
    @Column(length = 80)
    private String trackingNo;
    @Column(nullable = false)
    private LocalDateTime orderedAt;
    private LocalDateTime promisedShipAt;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected CustomerOrder() {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public CustomerOrder(String orderNo, String externalOrderNo, String channelName, String customerName,
                         String customerPhone, String itemSummary, int itemCount, BigDecimal totalAmount,
                         BigDecimal paidAmount, Status status, String warehouseName, LocalDateTime orderedAt,
                         LocalDateTime promisedShipAt) {
        this.orderNo = orderNo;
        this.externalOrderNo = externalOrderNo;
        this.channelName = channelName;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.itemSummary = itemSummary;
        this.itemCount = itemCount;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.status = status;
        this.warehouseName = warehouseName;
        this.orderedAt = orderedAt;
        this.promisedShipAt = promisedShipAt;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void changeStatus(Status status) { this.status = status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void markShipped(String carrierName, String trackingNo) {
        this.carrierName = carrierName;
        this.trackingNo = trackingNo;
        this.status = Status.SHIPPED;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getOrderNo() { return orderNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getExternalOrderNo() { return externalOrderNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getChannelName() { return channelName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCustomerName() { return customerName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCustomerPhone() { return customerPhone; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getItemSummary() { return itemSummary; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public int getItemCount() { return itemCount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getTotalAmount() { return totalAmount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getPaidAmount() { return paidAmount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getWarehouseName() { return warehouseName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCarrierName() { return carrierName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getTrackingNo() { return trackingNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getOrderedAt() { return orderedAt; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getPromisedShipAt() { return promisedShipAt; }
}
