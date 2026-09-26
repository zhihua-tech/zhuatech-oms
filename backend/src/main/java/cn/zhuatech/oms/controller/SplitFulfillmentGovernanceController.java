/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.controller;

import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.SplitFulfillmentGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@RestController
@RequestMapping("/api/enterprise/oms")
public class SplitFulfillmentGovernanceController {
    private final SplitFulfillmentGovernanceService service;

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public SplitFulfillmentGovernanceController(SplitFulfillmentGovernanceService service) {
        this.service = service;
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @PostMapping("/split-fulfillment")
    public ApiResponse<SplitFulfillmentGovernanceService.Assessment> assess(
            @Valid @RequestBody SplitFulfillmentGovernanceService.Request request) {
        return ApiResponse.ok("拆单履约评估完成", service.assess(request));
    }
}
