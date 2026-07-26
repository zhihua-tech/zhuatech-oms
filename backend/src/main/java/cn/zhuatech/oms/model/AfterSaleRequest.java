/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "oms_after_sale")
public class AfterSaleRequest extends BaseEntity {
    public enum Type { REFUND_ONLY, RETURN_REFUND, EXCHANGE }
    public enum Status { PENDING, APPROVED, REJECTED, PROCESSING, COMPLETED }

    @Column(nullable = false, unique = true, length = 40)
    private String requestNo;
    @Column(nullable = false, length = 40)
    private String orderNo;
    @Column(nullable = false, length = 80)
    private String customerName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private Type type;
    @Column(nullable = false, length = 255)
    private String reason;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;
    @Column(nullable = false)
    private LocalDateTime requestedAt;
    @Column(length = 80)
    private String handlerName;

    protected AfterSaleRequest() {}

    public AfterSaleRequest(String requestNo, String orderNo, String customerName, Type type, String reason,
                            BigDecimal amount, Status status, LocalDateTime requestedAt, String handlerName) {
        this.requestNo = requestNo;
        this.orderNo = orderNo;
        this.customerName = customerName;
        this.type = type;
        this.reason = reason;
        this.amount = amount;
        this.status = status;
        this.requestedAt = requestedAt;
        this.handlerName = handlerName;
    }

    public String getRequestNo() { return requestNo; }
    public String getOrderNo() { return orderNo; }
    public String getCustomerName() { return customerName; }
    public Type getType() { return type; }
    public String getReason() { return reason; }
    public BigDecimal getAmount() { return amount; }
    public Status getStatus() { return status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public String getHandlerName() { return handlerName; }
}
