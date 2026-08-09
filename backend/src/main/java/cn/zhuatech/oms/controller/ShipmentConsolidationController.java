/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oms.controller;

import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.ShipmentConsolidationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oms/insights")
public class ShipmentConsolidationController {
    private final ShipmentConsolidationService service;

    public ShipmentConsolidationController(ShipmentConsolidationService service) {
        this.service = service;
    }

    @PostMapping("/shipment-consolidation")
    public ApiResponse<ShipmentConsolidationService.Result> evaluate(
        @Valid @RequestBody ShipmentConsolidationService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
