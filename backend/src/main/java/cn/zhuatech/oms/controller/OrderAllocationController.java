/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oms.controller;

import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.OrderAllocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oms/insights")
public class OrderAllocationController {
    private final OrderAllocationService service;
    public OrderAllocationController(OrderAllocationService service) { this.service = service; }

    @PostMapping("/order-allocation")
    public ApiResponse<OrderAllocationService.Result> allocate(@Valid @RequestBody OrderAllocationService.Request request) {
        return ApiResponse.ok(service.allocate(request));
    }
}
