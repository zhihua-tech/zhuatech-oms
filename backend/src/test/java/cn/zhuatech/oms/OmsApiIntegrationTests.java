/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OmsApiIntegrationTests {
    @Autowired MockMvc mvc;

    @Test
    void managerCanReadDashboardAndOrderChain() throws Exception {
        String token = login("demo", "Demo@2026", "MANAGER");
        mvc.perform(get("/api/oms/dashboard").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.todayOrders").isNumber())
            .andExpect(jsonPath("$.data.channelWarnings").value(1));
        mvc.perform(get("/api/oms/orders").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[?(@.orderNo == 'OMS202607260001')]").isNotEmpty());
        mvc.perform(get("/api/oms/shipments").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void managerCanCreateManualOrder() throws Exception {
        String token = login("demo", "Demo@2026", "MANAGER");
        mvc.perform(post("/api/oms/orders").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"channelName\":\"线下测试渠道\",\"customerName\":\"个人学习客户\",\"customerPhone\":\"13800000000\",\"itemSummary\":\"测试商品 × 1\",\"itemCount\":1,\"totalAmount\":99.00,\"warehouseName\":\"测试仓\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.status").value("PAID"));
    }

    @Test
    void unauthenticatedOmsRequestIsRejected() throws Exception {
        mvc.perform(get("/api/oms/dashboard")).andExpect(status().isForbidden());
    }

    private String login(String username, String password, String expectedRole) throws Exception {
        String body = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.user.role").value(expectedRole))
            .andReturn().getResponse().getContentAsString();
        return JsonPath.read(body, "$.data.token");
    }
}
