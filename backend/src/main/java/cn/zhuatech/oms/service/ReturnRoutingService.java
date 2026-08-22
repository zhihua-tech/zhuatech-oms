/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnRoutingService {
    public Result route(Request request) {
        String route;
        if (request.daysSinceDelivery() > 30 && request.conditionScore() < 40) route = "REJECT_REVIEW";
        else if (request.conditionScore() >= 85 && request.sellableStockGap() > 0) route = "RESTOCK";
        else if (request.conditionScore() >= 55 && request.refurbishmentCost().compareTo(request.itemValue().multiply(new BigDecimal("0.35"))) <= 0) route = "REFURBISH";
        else route = "INSPECT";

        String destination = request.destinationDistanceKm() <= 80 ? "NEAREST_RETURN_CENTER" : "REGIONAL_RETURN_HUB";
        BigDecimal recoveryRate = switch (route) {
            case "RESTOCK" -> new BigDecimal("0.95");
            case "REFURBISH" -> new BigDecimal("0.65");
            case "INSPECT" -> new BigDecimal("0.40");
            default -> BigDecimal.ZERO;
        };
        BigDecimal estimatedRecovery = request.itemValue().multiply(recoveryRate).subtract(
            route.equals("REFURBISH") ? request.refurbishmentCost() : BigDecimal.ZERO).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
        List<String> actions = new ArrayList<>();
        if (route.equals("RESTOCK")) actions.add("质检通过后恢复可售库存并关联原订单");
        if (route.equals("REFURBISH")) actions.add("生成翻新工单并复核维修成本上限");
        if (route.equals("INSPECT")) actions.add("进入人工质检队列并记录商品成色证据");
        if (route.equals("REJECT_REVIEW")) actions.add("转客服复核退货时效与商品状态");
        if (request.destinationDistanceKm() > 80) actions.add("合并区域逆向物流批次以控制运费");
        return new Result(request.returnNo(), route, destination, estimatedRecovery, actions);
    }

    public record Request(@NotBlank String returnNo, @Min(0) @Max(100) int conditionScore,
                          @Min(0) int daysSinceDelivery,
                          @DecimalMin("0") BigDecimal itemValue,
                          @DecimalMin("0") BigDecimal refurbishmentCost,
                          @Min(0) int destinationDistanceKm,
                          @Min(0) int sellableStockGap) {}
    public record Result(String returnNo, String route, String destination,
                         BigDecimal estimatedRecovery, List<String> actions) {}
}
