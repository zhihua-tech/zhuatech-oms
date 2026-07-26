/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.controller;

import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.dto.OmsDto.*;
import cn.zhuatech.oms.model.*;
import cn.zhuatech.oms.repository.*;
import cn.zhuatech.oms.service.OmsService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@RestController
@RequestMapping("/api/oms")
public class OmsController {
    private final CustomerOrderRepository orders;
    private final ShipmentRepository shipments;
    private final AfterSaleRepository afterSales;
    private final SalesChannelRepository channels;
    private final OrderEventRepository events;
    private final OmsService service;

    public OmsController(CustomerOrderRepository orders, ShipmentRepository shipments,
                         AfterSaleRepository afterSales, SalesChannelRepository channels,
                         OrderEventRepository events, OmsService service) {
        this.orders = orders;
        this.shipments = shipments;
        this.afterSales = afterSales;
        this.channels = channels;
        this.events = events;
        this.service = service;
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardView> dashboard() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        long pendingShipment = shipments.countByStatusIn(List.of(Shipment.Status.PENDING,
            Shipment.Status.PICKING, Shipment.Status.PACKED));
        long shippingExceptions = shipments.countByStatus(Shipment.Status.EXCEPTION);
        long completed = orders.countByStatusIn(List.of(CustomerOrder.Status.SHIPPED,
            CustomerOrder.Status.COMPLETED));
        long active = orders.countByStatusIn(List.of(CustomerOrder.Status.PAID, CustomerOrder.Status.ALLOCATING,
            CustomerOrder.Status.READY_TO_SHIP, CustomerOrder.Status.SHIPPED, CustomerOrder.Status.COMPLETED));
        BigDecimal rate = active == 0 ? BigDecimal.ZERO
            : BigDecimal.valueOf(completed * 100.0 / active).setScale(1, java.math.RoundingMode.HALF_UP);
        return ApiResponse.ok(new DashboardView(orders.sumBetween(start, end),
            orders.countByOrderedAtBetween(start, end),
            orders.countByStatusIn(List.of(CustomerOrder.Status.PAID, CustomerOrder.Status.ALLOCATING)),
            pendingShipment,
            afterSales.countByStatusIn(List.of(AfterSaleRequest.Status.PENDING, AfterSaleRequest.Status.PROCESSING)),
            channels.countBySyncStatusNot(SalesChannel.SyncStatus.NORMAL), shippingExceptions, rate));
    }

    @GetMapping("/orders")
    public ApiResponse<List<OrderView>> orders() {
        return ApiResponse.ok(orders.findAllByOrderByOrderedAtDesc().stream().map(OrderView::from).toList());
    }

    @PostMapping("/orders")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','SALES')")
    public ApiResponse<OrderView> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return ApiResponse.ok("订单创建成功", OrderView.from(service.createOrder(request)));
    }

    @PatchMapping("/orders/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','SALES','WAREHOUSE')")
    public ApiResponse<OrderView> changeStatus(@PathVariable Long id,
                                               @Valid @RequestBody OrderStatusRequest request) {
        return ApiResponse.ok(OrderView.from(service.changeOrderStatus(id, request.status())));
    }

    @GetMapping("/shipments")
    public ApiResponse<List<ShipmentView>> shipments() {
        return ApiResponse.ok(shipments.findAllByOrderByIdDesc().stream().map(ShipmentView::from).toList());
    }

    @GetMapping("/after-sales")
    public ApiResponse<List<AfterSaleView>> afterSales() {
        return ApiResponse.ok(afterSales.findAllByOrderByRequestedAtDesc().stream().map(AfterSaleView::from).toList());
    }

    @PostMapping("/after-sales")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','SALES')")
    public ApiResponse<AfterSaleView> createAfterSale(@Valid @RequestBody AfterSaleCreateRequest request) {
        return ApiResponse.ok("售后申请创建成功", AfterSaleView.from(service.createAfterSale(request)));
    }

    @GetMapping("/channels")
    public ApiResponse<List<ChannelView>> channels() {
        return ApiResponse.ok(channels.findAllByOrderByTodayAmountDesc().stream().map(ChannelView::from).toList());
    }

    @GetMapping("/events")
    public ApiResponse<List<EventView>> events() {
        return ApiResponse.ok(events.findTop20ByOrderByOccurredAtDesc().stream().map(EventView::from).toList());
    }
}
