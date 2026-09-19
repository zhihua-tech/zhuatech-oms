/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.controller;
import cn.zhuatech.oms.common.ApiResponse;import cn.zhuatech.oms.service.FulfillmentExceptionGovernanceService;
import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/oms")
public class FulfillmentExceptionGovernanceController{
 private final FulfillmentExceptionGovernanceService service;/**
                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                              */
public FulfillmentExceptionGovernanceController(FulfillmentExceptionGovernanceService service){this.service=service;}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/fulfillment-exception")public ApiResponse<FulfillmentExceptionGovernanceService.Assessment> assess(@Valid @RequestBody FulfillmentExceptionGovernanceService.Request request){return ApiResponse.ok("履约异常恢复评估完成",service.assess(request));}
}
