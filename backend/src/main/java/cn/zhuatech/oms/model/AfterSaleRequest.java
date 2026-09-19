/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity
@Table(name = "oms_after_sale")
public class AfterSaleRequest extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Type { REFUND_ONLY, RETURN_REFUND, EXCHANGE }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
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

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected AfterSaleRequest() {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
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

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getRequestNo() { return requestNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getOrderNo() { return orderNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCustomerName() { return customerName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Type getType() { return type; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getReason() { return reason; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getAmount() { return amount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getRequestedAt() { return requestedAt; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getHandlerName() { return handlerName; }
}
