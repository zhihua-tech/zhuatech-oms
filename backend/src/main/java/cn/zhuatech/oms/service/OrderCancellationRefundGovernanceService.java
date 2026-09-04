/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderCancellationRefundGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.cancellationPolicyMatched()) blockers.add("取消原因不符合订单或渠道政策");
        if (!request.shipmentIntercepted()) blockers.add("已下发仓库或承运商的发运尚未拦截");
        if (!request.inventoryReservationReleased()) blockers.add("库存占用尚未释放或回滚");
        if (!request.paymentTransactionLinked()) blockers.add("退款未关联原支付交易");
        if (!request.refundAmountReconciled()) blockers.add("退款金额与订单、优惠及实付未对平");
        if (!request.promotionReversed()) blockers.add("优惠券、积分或促销权益未回退");
        if (!request.taxDocumentHandled()) blockers.add("发票红冲或税务处理未完成");
        if (!request.fraudReviewClear()) blockers.add("欺诈、盗刷或异常退款复核未通过");
        if (!request.customerIdentityVerified()) blockers.add("高风险退款的客户身份未核验");
        if (!request.financeApproved()) blockers.add("财务尚未批准资金退回");
        if (!request.businessOwnerApproved()) blockers.add("订单业务负责人尚未批准取消");
        if (!request.makerCheckerSeparated()) blockers.add("退款经办人与审批人未职责分离");
        if (!request.auditReady()) blockers.add("订单、物流、支付、审批和退款证据链不完整");
        if (!request.customerNoticeReady()) actions.add("生成退款金额、路径和时效通知");
        if (!request.channelSyncReady()) actions.add("准备渠道取消状态及退款结果回传");
        if (!request.returnMonitoringReady()) actions.add("配置退款到账失败与超时监控");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED : !actions.isEmpty() ? Decision.REVIEW : Decision.REFUND;
        return new Assessment(request.requestId(), request.orderNo(), request.refundAmount(), decision,
                List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String requestId, @NotBlank String orderNo,
                          @DecimalMin("0.01") BigDecimal refundAmount,
                          boolean cancellationPolicyMatched, boolean shipmentIntercepted,
                          boolean inventoryReservationReleased, boolean paymentTransactionLinked,
                          boolean refundAmountReconciled, boolean promotionReversed,
                          boolean taxDocumentHandled, boolean fraudReviewClear,
                          boolean customerIdentityVerified, boolean financeApproved,
                          boolean businessOwnerApproved, boolean makerCheckerSeparated, boolean auditReady,
                          boolean customerNoticeReady, boolean channelSyncReady,
                          boolean returnMonitoringReady) {}
    public record Assessment(String requestId, String orderNo, BigDecimal refundAmount, Decision decision,
                             List<String> blockers, List<String> actions) {}
    public enum Decision { REFUND, REVIEW, BLOCKED }
}
