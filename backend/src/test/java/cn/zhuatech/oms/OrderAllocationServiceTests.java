/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oms;

import cn.zhuatech.oms.service.OrderAllocationService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderAllocationServiceTests {
    private final OrderAllocationService service = new OrderAllocationService();

    @Test void selectsBestFullStockNode() {
        var result = service.allocate(new OrderAllocationService.Request("SO-1001", 8, 2, List.of(
            new OrderAllocationService.NodeCandidate("SH-A", 20, 8, 35, 70),
            new OrderAllocationService.NodeCandidate("SZ-A", 15, 16, 900, 90))));
        assertThat(result.decision()).isEqualTo("SINGLE_NODE");
        assertThat(result.selectedNodes()).containsExactly("SH-A");
    }

    @Test void splitsWhenNoNodeCanFulfillAlone() {
        var result = service.allocate(new OrderAllocationService.Request("SO-1002", 10, 2, List.of(
            new OrderAllocationService.NodeCandidate("SH-A", 6, 8, 35, 80),
            new OrderAllocationService.NodeCandidate("HZ-A", 5, 10, 180, 85))));
        assertThat(result.decision()).isEqualTo("SPLIT");
        assertThat(result.backorderQuantity()).isZero();
    }
}
