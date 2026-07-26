/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.dto;

import cn.zhuatech.oms.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class OmsDto {
    private OmsDto() {}

    public record DashboardView(BigDecimal todayGmv, long todayOrders, long pendingAllocation,
                                long pendingShipment, long pendingAfterSales, long channelWarnings,
                                long shippingExceptions, BigDecimal fulfillmentRate) {}

    public record OrderView(Long id, String orderNo, String externalOrderNo, String channelName,
                            String customerName, String customerPhone, String itemSummary, int itemCount,
                            BigDecimal totalAmount, BigDecimal paidAmount, String status,
                            String warehouseName, String carrierName, String trackingNo,
                            LocalDateTime orderedAt, LocalDateTime promisedShipAt) {
        public static OrderView from(CustomerOrder order) {
            return new OrderView(order.getId(), order.getOrderNo(), order.getExternalOrderNo(),
                order.getChannelName(), order.getCustomerName(), order.getCustomerPhone(),
                order.getItemSummary(), order.getItemCount(), order.getTotalAmount(), order.getPaidAmount(),
                order.getStatus().name(), order.getWarehouseName(), order.getCarrierName(),
                order.getTrackingNo(), order.getOrderedAt(), order.getPromisedShipAt());
        }
    }

    public record ShipmentView(Long id, String shipmentNo, String orderNo, String warehouseName,
                               String carrierName, String trackingNo, String status, int itemCount,
                               LocalDateTime shippedAt, LocalDateTime expectedDeliveryAt) {
        public static ShipmentView from(Shipment shipment) {
            return new ShipmentView(shipment.getId(), shipment.getShipmentNo(), shipment.getOrderNo(),
                shipment.getWarehouseName(), shipment.getCarrierName(), shipment.getTrackingNo(),
                shipment.getStatus().name(), shipment.getItemCount(), shipment.getShippedAt(),
                shipment.getExpectedDeliveryAt());
        }
    }

    public record AfterSaleView(Long id, String requestNo, String orderNo, String customerName,
                                String type, String reason, BigDecimal amount, String status,
                                LocalDateTime requestedAt, String handlerName) {
        public static AfterSaleView from(AfterSaleRequest request) {
            return new AfterSaleView(request.getId(), request.getRequestNo(), request.getOrderNo(),
                request.getCustomerName(), request.getType().name(), request.getReason(), request.getAmount(),
                request.getStatus().name(), request.getRequestedAt(), request.getHandlerName());
        }
    }

    public record ChannelView(Long id, String code, String name, String type, String status,
                              String syncStatus, long todayOrders, BigDecimal todayAmount,
                              LocalDateTime lastSyncAt) {
        public static ChannelView from(SalesChannel channel) {
            return new ChannelView(channel.getId(), channel.getCode(), channel.getName(),
                channel.getType().name(), channel.getStatus().name(), channel.getSyncStatus().name(),
                channel.getTodayOrders(), channel.getTodayAmount(), channel.getLastSyncAt());
        }
    }

    public record EventView(Long id, String orderNo, String eventType, String title,
                            String description, String operatorName, LocalDateTime occurredAt) {
        public static EventView from(OrderEvent event) {
            return new EventView(event.getId(), event.getOrderNo(), event.getEventType(), event.getTitle(),
                event.getDescription(), event.getOperatorName(), event.getOccurredAt());
        }
    }

    public record OrderCreateRequest(
        @NotBlank String channelName,
        @NotBlank String customerName,
        String customerPhone,
        @NotBlank String itemSummary,
        @Min(1) int itemCount,
        @DecimalMin("0.01") BigDecimal totalAmount,
        String warehouseName) {}

    public record OrderStatusRequest(@NotBlank String status) {}

    public record AfterSaleCreateRequest(
        @NotBlank String orderNo,
        @NotBlank String customerName,
        @NotBlank String type,
        @NotBlank String reason,
        @DecimalMin("0.00") BigDecimal amount) {}
}
