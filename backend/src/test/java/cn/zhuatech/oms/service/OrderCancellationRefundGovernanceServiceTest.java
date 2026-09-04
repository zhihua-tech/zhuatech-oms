/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderCancellationRefundGovernanceServiceTest {
    private final OrderCancellationRefundGovernanceService service = new OrderCancellationRefundGovernanceService();

    @Test void refundsControlledCancellation() {
        var result = service.assess(request(true, true, true));
        assertEquals(OrderCancellationRefundGovernanceService.Decision.REFUND, result.decision());
        assertTrue(result.blockers().isEmpty());
        assertTrue(result.actions().isEmpty());
    }

    @Test void reviewsRefundWithOperationalActions() {
        var result = service.assess(request(false, false, false));
        assertEquals(OrderCancellationRefundGovernanceService.Decision.REVIEW, result.decision());
        assertEquals(3, result.actions().size());
    }

    @Test void blocksUncontrolledCancellationRefund() {
        var result = service.assess(new OrderCancellationRefundGovernanceService.Request("CANCEL-003", "ORD-003",
                new BigDecimal("1280.00"), false, false, false, false, false, false, false, false,
                false, false, false, false, false, true, true, true));
        assertEquals(OrderCancellationRefundGovernanceService.Decision.BLOCKED, result.decision());
        assertEquals(13, result.blockers().size());
    }

    private OrderCancellationRefundGovernanceService.Request request(boolean notice, boolean channel, boolean monitoring) {
        return new OrderCancellationRefundGovernanceService.Request("CANCEL-001", "ORD-001", new BigDecimal("1280.00"),
                true, true, true, true, true, true, true, true, true, true, true, true, true,
                notice, channel, monitoring);
    }
}
