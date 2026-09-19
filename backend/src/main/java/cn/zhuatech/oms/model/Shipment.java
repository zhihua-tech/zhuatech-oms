/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity
@Table(name = "oms_shipment")
public class Shipment extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { PENDING, PICKING, PACKED, SHIPPED, DELIVERED, EXCEPTION }

    @Column(nullable = false, unique = true, length = 40)
    private String shipmentNo;
    @Column(nullable = false, length = 40)
    private String orderNo;
    @Column(nullable = false, length = 80)
    private String warehouseName;
    @Column(length = 80)
    private String carrierName;
    @Column(length = 80)
    private String trackingNo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;
    @Column(nullable = false)
    private int itemCount;
    private LocalDateTime shippedAt;
    private LocalDateTime expectedDeliveryAt;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected Shipment() {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Shipment(String shipmentNo, String orderNo, String warehouseName, String carrierName,
                    String trackingNo, Status status, int itemCount, LocalDateTime shippedAt,
                    LocalDateTime expectedDeliveryAt) {
        this.shipmentNo = shipmentNo;
        this.orderNo = orderNo;
        this.warehouseName = warehouseName;
        this.carrierName = carrierName;
        this.trackingNo = trackingNo;
        this.status = status;
        this.itemCount = itemCount;
        this.shippedAt = shippedAt;
        this.expectedDeliveryAt = expectedDeliveryAt;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getShipmentNo() { return shipmentNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getOrderNo() { return orderNo; }
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
    public Status getStatus() { return status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public int getItemCount() { return itemCount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getShippedAt() { return shippedAt; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getExpectedDeliveryAt() { return expectedDeliveryAt; }
}
