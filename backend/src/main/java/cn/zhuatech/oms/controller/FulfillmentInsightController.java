/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oms.controller;

import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.FulfillmentPromiseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oms/insights")
public class FulfillmentInsightController {
    private final FulfillmentPromiseService service;
    public FulfillmentInsightController(FulfillmentPromiseService service) { this.service = service; }

    @PostMapping("/fulfillment-promise")
    public ApiResponse<FulfillmentPromiseService.Result> evaluate(@Valid @RequestBody FulfillmentPromiseService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
