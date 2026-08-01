/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oms.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FulfillmentPromiseService {
    public Result evaluate(Request request) {
        int score = Math.min(100, (int) Math.round((1 - request.stockCoverage()) * 40
            + Math.min(25, request.warehouseBacklog() / 10.0)
            + Math.min(25, (double) request.carrierDelayHours() / request.promisedHours() * 25)
            + (request.vipOrder() ? 10 : 0)));
        String decision = score >= 75 ? "REPLAN" : score >= 50 ? "EXPEDITE" : "ON_TIME";
        List<String> actions = new ArrayList<>();
        if (request.stockCoverage() < .7) actions.add("锁定可用库存并检查替代仓履约能力");
        if (request.warehouseBacklog() >= 100) actions.add("提升订单波次优先级");
        if (request.carrierDelayHours() > 0) actions.add("确认承运商新时效并更新客户承诺");
        if (actions.isEmpty()) actions.add("按当前履约计划执行");
        return new Result(request.orderNo(), score, decision,
            Math.max(0, request.promisedHours() - request.carrierDelayHours()), actions);
    }

    public record Request(@NotBlank String orderNo,
                          @DecimalMin("0") @DecimalMax("1") double stockCoverage,
                          @Min(0) int warehouseBacklog, @Min(0) int carrierDelayHours,
                          @Positive int promisedHours, boolean vipOrder) {}
    public record Result(String orderNo, int riskScore, String decision,
                         int remainingBufferHours, List<String> actions) {}
}
