/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "oms_shipment")
public class Shipment extends BaseEntity {
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

    protected Shipment() {}

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

    public String getShipmentNo() { return shipmentNo; }
    public String getOrderNo() { return orderNo; }
    public String getWarehouseName() { return warehouseName; }
    public String getCarrierName() { return carrierName; }
    public String getTrackingNo() { return trackingNo; }
    public Status getStatus() { return status; }
    public int getItemCount() { return itemCount; }
    public LocalDateTime getShippedAt() { return shippedAt; }
    public LocalDateTime getExpectedDeliveryAt() { return expectedDeliveryAt; }
}
