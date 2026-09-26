/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单拆单履约前检查库存节点、时效、支付风控、合规与拆单成本。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class SplitFulfillmentGovernanceService {
    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.paymentCaptured()) blockers.add("订单尚未完成支付确认");
        if (!request.fraudCleared()) blockers.add("订单未通过反欺诈审查");
        if (request.stockedNodes() == 0 || request.candidateNodes() == 0) blockers.add("没有可供分配的履约节点");
        if (request.estimatedTransitDays() > request.promisedDeliveryDays()) blockers.add("当前拆单方案无法满足承诺时效");
        if (request.customerRequiresSingleShipment() && request.plannedShipmentCount() > 1) {
            blockers.add("客户要求单包裹交付，不允许拆单");
        }
        if (request.restrictedGoods() && !request.compliantNodeSelected()) blockers.add("受限商品未分配至具备资质的履约节点");
        if (request.crossBorder() && !request.customsDocumentsReady()) blockers.add("跨境拆单缺少报关与合规单证");
        if (request.marginAfterFreight().compareTo(request.minimumMargin()) < 0) actions.add("拆单运费导致毛利低于阈值，申请例外审批");
        if (request.plannedShipmentCount() > 1 && !request.customerNotificationPlanned()) actions.add("向客户说明包裹数量、物流单号与到货时间");
        if (!request.auditEvidenceAttached()) actions.add("归档节点选择、库存锁定、运费与时效证据");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED
                : !actions.isEmpty() ? Decision.REVIEW : Decision.RELEASE;
        int usableNodes = Math.min(request.candidateNodes(), request.stockedNodes());
        return new Assessment(request.orderNo(), decision, usableNodes, request.plannedShipmentCount(),
                request.marginAfterFreight(), List.copyOf(blockers), List.copyOf(actions));
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Request(@NotBlank String orderNo, @Min(1) int orderLineCount,
                          @Min(0) int candidateNodes, @Min(0) int stockedNodes,
                          @Min(1) int promisedDeliveryDays, @Min(1) int estimatedTransitDays,
                          @Min(1) int plannedShipmentCount, boolean paymentCaptured,
                          boolean fraudCleared, boolean customerRequiresSingleShipment,
                          boolean restrictedGoods, boolean compliantNodeSelected,
                          boolean crossBorder, boolean customsDocumentsReady,
                          @NotNull @DecimalMin("0.00") BigDecimal marginAfterFreight,
                          @NotNull @DecimalMin("0.00") BigDecimal minimumMargin,
                          boolean customerNotificationPlanned, boolean auditEvidenceAttached) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Assessment(String orderNo, Decision decision, int usableNodes,
                             int shipmentCount, BigDecimal marginAfterFreight,
                             List<String> blockers, List<String> actions) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public enum Decision { RELEASE, REVIEW, BLOCKED }
}
