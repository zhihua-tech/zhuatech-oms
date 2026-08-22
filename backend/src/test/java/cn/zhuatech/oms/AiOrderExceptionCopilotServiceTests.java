/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms;
import cn.zhuatech.oms.ai.OpenAiCompatibleGateway;
import cn.zhuatech.oms.service.AiOrderExceptionCopilotService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
class AiOrderExceptionCopilotServiceTests {
    private final AiOrderExceptionCopilotService service = new AiOrderExceptionCopilotService(
        new OpenAiCompatibleGateway("local", "https://api.deepseek.com", "deepseek-chat", ""));
    @Test void flagsCriticalOrderException() {
        var result = service.evaluate(new AiOrderExceptionCopilotService.Request("SO-2026-1008", 24, 22,
            new BigDecimal("55"), false, false, new BigDecimal("60"), 5, true));
        assertThat(result.status()).isEqualTo("CRITICAL");
        assertThat(result.actions()).hasSizeGreaterThan(3);
    }
}
