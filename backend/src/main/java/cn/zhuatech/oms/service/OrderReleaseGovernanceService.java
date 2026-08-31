/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderReleaseGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.fraudCleared()) blockers.add("订单反欺诈校验未通过");
        if (!request.exportControlCleared()) blockers.add("出口管制或受限方筛查未通过");
        if (!request.paymentAuthorized()) blockers.add("付款授权无效或已过期");
        if (!request.addressValidated()) blockers.add("收货地址未完成标准化验证");
        if (!request.inventoryAllocated()) actions.add("完成可售库存锁定或重新分仓");
        if (!request.priceApproved()) actions.add("完成价格与折扣授权");
        if (!request.customerCreditClear()) actions.add("解除客户信用冻结或取得例外审批");
        if (request.manualOverride() && !request.overrideApproved()) blockers.add("人工例外尚未获授权");

        Decision decision = !blockers.isEmpty() ? Decision.HOLD
                : !actions.isEmpty() ? Decision.APPROVAL_REQUIRED : Decision.RELEASE;
        return new Assessment(request.orderNo(), decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String orderNo, boolean fraudCleared,
                          boolean exportControlCleared, boolean paymentAuthorized,
                          boolean addressValidated, boolean inventoryAllocated,
                          boolean priceApproved, boolean customerCreditClear,
                          boolean manualOverride, boolean overrideApproved) {}
    public record Assessment(String orderNo, Decision decision, List<String> blockers,
                             List<String> actions) {}
    public enum Decision { RELEASE, APPROVAL_REQUIRED, HOLD }
}
