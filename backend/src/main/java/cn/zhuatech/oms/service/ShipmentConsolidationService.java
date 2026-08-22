/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentConsolidationService {
    public Result evaluate(Request request) {
        BigDecimal savings = request.currentFreight().subtract(request.consolidatedFreight())
            .max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
        BigDecimal savingRate = request.currentFreight().signum() == 0 ? BigDecimal.ZERO
            : savings.divide(request.currentFreight(), 4, RoundingMode.HALF_UP);

        int riskScore = 0;
        if (request.extraHandlingHours() > request.promisedBufferHours()) riskScore += 55;
        if (request.fragile()) riskScore += 25;
        if (request.packageCount() >= 4) riskScore += 15;
        if (request.totalWeightKg().compareTo(new BigDecimal("30")) > 0) riskScore += 10;
        riskScore = Math.min(100, riskScore);

        String decision = riskScore >= 55 ? "KEEP_SPLIT"
            : savingRate.compareTo(new BigDecimal("0.12")) >= 0 ? "CONSOLIDATE" : "REVIEW";
        List<String> actions = new ArrayList<>();
        if (request.extraHandlingHours() > request.promisedBufferHours()) actions.add("保留拆单，避免合包等待造成承诺超时");
        if (request.fragile()) actions.add("按易碎品规则复核包装兼容性");
        if ("CONSOLIDATE".equals(decision)) actions.add("合并发运并回写新的运单与费用分摊");
        if (actions.isEmpty()) actions.add("人工确认仓内合包能力与承运商计费规则");
        return new Result(request.orderNo(), savings, savingRate, riskScore, decision, actions);
    }

    public record Request(@NotBlank String orderNo, @Min(1) int packageCount,
                          @DecimalMin("0.01") BigDecimal totalWeightKg,
                          @DecimalMin("0") BigDecimal currentFreight,
                          @DecimalMin("0") BigDecimal consolidatedFreight,
                          @Min(0) int extraHandlingHours, @Min(0) int promisedBufferHours,
                          boolean fragile) {}

    public record Result(String orderNo, BigDecimal estimatedSavings, BigDecimal savingRate,
                         int riskScore, String decision, List<String> actions) {}
}
