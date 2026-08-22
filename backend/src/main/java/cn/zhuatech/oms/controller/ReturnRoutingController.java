/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.controller;

import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.ReturnRoutingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oms/insights")
public class ReturnRoutingController {
    private final ReturnRoutingService service;
    public ReturnRoutingController(ReturnRoutingService service) { this.service = service; }

    @PostMapping("/return-routing")
    public ApiResponse<ReturnRoutingService.Result> route(@Valid @RequestBody ReturnRoutingService.Request request) {
        return ApiResponse.ok(service.route(request));
    }
}
