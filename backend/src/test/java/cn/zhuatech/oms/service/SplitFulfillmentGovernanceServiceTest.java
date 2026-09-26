/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
class SplitFulfillmentGovernanceServiceTest {
    private final SplitFulfillmentGovernanceService service = new SplitFulfillmentGovernanceService();

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void releasesCompliantSplitPlan() {
        var result = service.assess(request(2, 2, 2, 3, "80", true, true));
        assertThat(result.decision()).isEqualTo(SplitFulfillmentGovernanceService.Decision.RELEASE);
        assertThat(result.usableNodes()).isEqualTo(2);
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void blocksLateSingleShipmentConflict() {
        var result = service.assess(request(2, 2, 2, 8, "80", true, false));
        assertThat(result.decision()).isEqualTo(SplitFulfillmentGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).anyMatch(item -> item.contains("承诺时效"));
        assertThat(result.blockers()).anyMatch(item -> item.contains("单包裹"));
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void reviewsLowMarginPlanWithoutCustomerNotice() {
        var result = service.assess(request(2, 2, 2, 3, "20", false, true));
        assertThat(result.decision()).isEqualTo(SplitFulfillmentGovernanceService.Decision.REVIEW);
        assertThat(result.actions()).hasSize(3);
    }

    private SplitFulfillmentGovernanceService.Request request(int candidates, int stocked,
                                                               int shipments, int transitDays,
                                                               String margin, boolean prepared,
                                                               boolean splitAllowed) {
        return new SplitFulfillmentGovernanceService.Request("ORD-100", 4, candidates, stocked,
                5, transitDays, shipments, true, true, !splitAllowed,
                false, true, false, true, new BigDecimal(margin), new BigDecimal("50"),
                prepared, prepared);
    }
}
