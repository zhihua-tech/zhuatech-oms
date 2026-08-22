/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import cn.zhuatech.oms.ai.OpenAiCompatibleGateway;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiOrderExceptionCopilotService {
    private final OpenAiCompatibleGateway gateway;
    public AiOrderExceptionCopilotService(OpenAiCompatibleGateway gateway) { this.gateway = gateway; }

    public Result evaluate(Request request) {
        int probability = 5;
        List<String> reasons = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        BigDecimal timeUsed = BigDecimal.valueOf(request.elapsedHours() * 100.0 / request.promisedHours());
        if (timeUsed.compareTo(BigDecimal.valueOf(80)) >= 0) { probability += 25; reasons.add("承诺时限已消耗超过 80%"); }
        if (request.inventoryReadyRate().compareTo(BigDecimal.valueOf(90)) < 0) { probability += 25; reasons.add("库存齐套率不足"); actions.add("协调替代库存或拆单发货"); }
        if (!Boolean.TRUE.equals(request.paymentVerified())) { probability += 20; reasons.add("付款校验未完成"); actions.add("升级财务核款并暂缓高风险出库"); }
        if (!Boolean.TRUE.equals(request.addressValid())) { probability += 25; reasons.add("收货地址校验失败"); actions.add("联系客户确认地址和联系方式"); }
        if (request.carrierCapacityRate().compareTo(BigDecimal.valueOf(80)) < 0) { probability += 20; reasons.add("承运商运力紧张"); actions.add("切换备选承运商或调整揽收窗口"); }
        if (request.customerPriority() >= 4) { actions.add("进入高优先级客户沟通与履约看板"); }
        if (Boolean.TRUE.equals(request.splitShipmentAllowed())) actions.add("评估先发齐套商品以降低整体延迟");
        probability = Math.min(100, probability);
        if (reasons.isEmpty()) reasons.add("订单关键履约信号正常");
        if (actions.isEmpty()) actions.add("保持当前履约路径并持续跟踪节点状态");

        String context = "订单=%s，异常概率=%d，原因=%s，可执行动作=%s"
            .formatted(request.orderNumber(), probability, reasons, actions);
        var enhanced = gateway.complete("你是订单履约异常助手，请给出内部处置、客户沟通和备选履约方案。", context);
        var metadata = gateway.metadata();
        return new Result(probability, probability >= 70 ? "CRITICAL" : probability >= 40 ? "AT_RISK" : "NORMAL",
            List.copyOf(reasons), List.copyOf(actions), enhanced.orElse("建议优先执行：" + actions.getFirst()),
            enhanced.isPresent() ? "EXTERNAL_MODEL" : "LOCAL_RULES", metadata.provider(), metadata.model());
    }

    public record Request(@NotBlank String orderNumber, @Min(1) int promisedHours, @Min(0) int elapsedHours,
                          @DecimalMin("0") @DecimalMax("100") BigDecimal inventoryReadyRate,
                          @NotNull Boolean paymentVerified, @NotNull Boolean addressValid,
                          @DecimalMin("0") @DecimalMax("100") BigDecimal carrierCapacityRate,
                          @Min(1) int customerPriority, @NotNull Boolean splitShipmentAllowed) {}
    public record Result(int exceptionProbability, String status, List<String> reasons, List<String> actions,
                         String copilotAdvice, String aiMode, String provider, String model) {}
}
