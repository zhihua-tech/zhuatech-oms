/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.controller;

import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.OrderReleaseGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/oms")
public class OrderReleaseGovernanceController {
    private final OrderReleaseGovernanceService service;
    public OrderReleaseGovernanceController(OrderReleaseGovernanceService service) { this.service = service; }

    @PostMapping("/order-release")
    public ApiResponse<OrderReleaseGovernanceService.Assessment> assess(
            @Valid @RequestBody OrderReleaseGovernanceService.Request request) {
        return ApiResponse.ok("订单放行评估完成", service.assess(request));
    }
}
