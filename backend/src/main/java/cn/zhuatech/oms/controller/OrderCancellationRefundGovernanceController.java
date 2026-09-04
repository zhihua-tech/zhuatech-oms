/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.controller;

import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.OrderCancellationRefundGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/oms")
public class OrderCancellationRefundGovernanceController {
    private final OrderCancellationRefundGovernanceService service;
    public OrderCancellationRefundGovernanceController(OrderCancellationRefundGovernanceService service) { this.service = service; }

    @PostMapping("/order-cancellation-refund")
    public ApiResponse<OrderCancellationRefundGovernanceService.Assessment> assess(
            @Valid @RequestBody OrderCancellationRefundGovernanceService.Request request) {
        return ApiResponse.ok("订单取消退款评估完成", service.assess(request));
    }
}
