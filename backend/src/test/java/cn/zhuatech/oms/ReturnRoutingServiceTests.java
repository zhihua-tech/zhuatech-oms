/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oms;

import cn.zhuatech.oms.service.ReturnRoutingService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class ReturnRoutingServiceTests {
    private final ReturnRoutingService service = new ReturnRoutingService();

    @Test void routesSellableItemBackToStock() {
        var result = service.route(new ReturnRoutingService.Request("R1001", 92, 7, new BigDecimal("1000"), new BigDecimal("80"), 40, 12));
        assertThat(result.route()).isEqualTo("RESTOCK");
        assertThat(result.estimatedRecovery()).isEqualByComparingTo("950.00");
    }

    @Test void sendsEconomicalRepairToRefurbishment() {
        var result = service.route(new ReturnRoutingService.Request("R1002", 70, 10, new BigDecimal("1000"), new BigDecimal("180"), 120, 0));
        assertThat(result.route()).isEqualTo("REFURBISH");
        assertThat(result.destination()).isEqualTo("REGIONAL_RETURN_HUB");
        assertThat(result.actions()).hasSize(2);
    }
}
