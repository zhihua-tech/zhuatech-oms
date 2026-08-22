/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.controller;
import cn.zhuatech.oms.common.ApiResponse;
import cn.zhuatech.oms.service.AiOrderExceptionCopilotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/oms/ai")
public class AiOrderExceptionCopilotController {
    private final AiOrderExceptionCopilotService service;
    public AiOrderExceptionCopilotController(AiOrderExceptionCopilotService service) { this.service = service; }
    @PostMapping("/order-exception")
    public ApiResponse<AiOrderExceptionCopilotService.Result> evaluate(@Valid @RequestBody AiOrderExceptionCopilotService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
