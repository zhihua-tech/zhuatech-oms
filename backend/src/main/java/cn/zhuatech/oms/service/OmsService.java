/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.service;

import cn.zhuatech.oms.common.BusinessException;
import cn.zhuatech.oms.dto.OmsDto.*;
import cn.zhuatech.oms.model.*;
import cn.zhuatech.oms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class OmsService {
    private final CustomerOrderRepository orders;
    private final AfterSaleRepository afterSales;
    private final OrderEventRepository events;
    private final CurrentUserService currentUser;

    public OmsService(CustomerOrderRepository orders, AfterSaleRepository afterSales,
                      OrderEventRepository events, CurrentUserService currentUser) {
        this.orders = orders;
        this.afterSales = afterSales;
        this.events = events;
        this.currentUser = currentUser;
    }

    @Transactional
    public CustomerOrder createOrder(OrderCreateRequest request) {
        String orderNo = "OMS" + System.currentTimeMillis();
        CustomerOrder order = orders.save(new CustomerOrder(orderNo, null, request.channelName(),
            request.customerName(), request.customerPhone(), request.itemSummary(), request.itemCount(),
            request.totalAmount(), request.totalAmount(), CustomerOrder.Status.PAID,
            request.warehouseName(), LocalDateTime.now(), LocalDateTime.now().plusHours(24)));
        events.save(new OrderEvent(orderNo, "ORDER_CREATED", "订单创建",
            "人工订单已进入履约队列", currentUser.get().getFullName(), LocalDateTime.now()));
        return order;
    }

    @Transactional
    public CustomerOrder changeOrderStatus(Long id, String rawStatus) {
        CustomerOrder order = orders.findById(id).orElseThrow(() -> new BusinessException("订单不存在"));
        try {
            CustomerOrder.Status status = CustomerOrder.Status.valueOf(rawStatus);
            order.changeStatus(status);
            events.save(new OrderEvent(order.getOrderNo(), "STATUS_CHANGED", "订单状态更新",
                "订单状态已变更为 " + status.name(), currentUser.get().getFullName(), LocalDateTime.now()));
            return order;
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("订单状态不正确");
        }
    }

    @Transactional
    public AfterSaleRequest createAfterSale(AfterSaleCreateRequest request) {
        try {
            String requestNo = "AS" + System.currentTimeMillis();
            AfterSaleRequest afterSale = afterSales.save(new AfterSaleRequest(requestNo, request.orderNo(),
                request.customerName(), AfterSaleRequest.Type.valueOf(request.type()), request.reason(),
                request.amount(), AfterSaleRequest.Status.PENDING, LocalDateTime.now(),
                currentUser.get().getFullName()));
            events.save(new OrderEvent(request.orderNo(), "AFTER_SALE", "售后申请创建", request.reason(),
                currentUser.get().getFullName(), LocalDateTime.now()));
            return afterSale;
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("售后类型不正确");
        }
    }
}
