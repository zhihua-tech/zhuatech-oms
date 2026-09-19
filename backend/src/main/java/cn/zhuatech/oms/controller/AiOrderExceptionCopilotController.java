/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.controller;
import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.AiOrderExceptionCopilotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/oms/ai")
public class AiOrderExceptionCopilotController {
    private final AiOrderExceptionCopilotService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AiOrderExceptionCopilotController(AiOrderExceptionCopilotService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/order-exception")
    public ApiResponse<AiOrderExceptionCopilotService.Result> evaluate(@Valid @RequestBody AiOrderExceptionCopilotService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
