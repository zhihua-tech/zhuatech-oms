/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oms;

import cn.zhuatech.oms.service.ShipmentConsolidationService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipmentConsolidationServiceTests {
    private final ShipmentConsolidationService service = new ShipmentConsolidationService();

    @Test
    void recommendsConsolidationWhenSavingsAreMaterialAndPromiseIsSafe() {
        var result = service.evaluate(new ShipmentConsolidationService.Request(
            "OMS-2026-0810", 3, new BigDecimal("12.5"), new BigDecimal("80"),
            new BigDecimal("55"), 2, 8, false));

        assertEquals("CONSOLIDATE", result.decision());
        assertEquals(new BigDecimal("25.00"), result.estimatedSavings());
        assertEquals(new BigDecimal("0.3125"), result.savingRate());
        assertTrue(result.actions().getFirst().contains("合并发运"));
    }

    @Test
    void keepsSplitWhenConsolidationWouldBreakPromise() {
        var result = service.evaluate(new ShipmentConsolidationService.Request(
            "OMS-2026-0811", 5, new BigDecimal("36"), new BigDecimal("120"),
            new BigDecimal("80"), 10, 4, true));

        assertEquals("KEEP_SPLIT", result.decision());
        assertEquals(100, result.riskScore());
    }
}
