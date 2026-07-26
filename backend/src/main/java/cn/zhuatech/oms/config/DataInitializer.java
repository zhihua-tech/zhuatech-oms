/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.config;

import cn.zhuatech.oms.model.*;
import cn.zhuatech.oms.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository users;
    private final CustomerOrderRepository orders;
    private final ShipmentRepository shipments;
    private final AfterSaleRepository afterSales;
    private final SalesChannelRepository channels;
    private final OrderEventRepository events;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository users, CustomerOrderRepository orders, ShipmentRepository shipments,
                           AfterSaleRepository afterSales, SalesChannelRepository channels,
                           OrderEventRepository events, PasswordEncoder encoder) {
        this.users = users;
        this.orders = orders;
        this.shipments = shipments;
        this.afterSales = afterSales;
        this.channels = channels;
        this.events = events;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (users.count() > 0) return;
        LocalDateTime now = LocalDateTime.now();

        users.save(new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员",
            UserAccount.Role.ADMIN, "数字化中心"));
        users.save(new UserAccount("demo", encoder.encode("Demo@2026"), "林运营",
            UserAccount.Role.MANAGER, "订单运营中心"));
        users.save(new UserAccount("operator", encoder.encode("Demo@2026"), "周专员",
            UserAccount.Role.SALES, "履约运营组"));

        channels.save(new SalesChannel("SELF", "品牌官网商城", SalesChannel.Type.SELF_OPERATED,
            SalesChannel.Status.ENABLED, SalesChannel.SyncStatus.NORMAL, 126, money("186420"), now.minusMinutes(2)));
        channels.save(new SalesChannel("TMALL", "天猫旗舰店", SalesChannel.Type.MARKETPLACE,
            SalesChannel.Status.ENABLED, SalesChannel.SyncStatus.NORMAL, 98, money("132860"), now.minusMinutes(3)));
        channels.save(new SalesChannel("JD", "京东自营店", SalesChannel.Type.MARKETPLACE,
            SalesChannel.Status.ENABLED, SalesChannel.SyncStatus.WARNING, 67, money("96480"), now.minusMinutes(18)));
        channels.save(new SalesChannel("OFFLINE", "华东线下渠道", SalesChannel.Type.DISTRIBUTOR,
            SalesChannel.Status.ENABLED, SalesChannel.SyncStatus.NORMAL, 23, money("72800"), now.minusMinutes(5)));

        orders.save(order("OMS202607260001", "TM202607261892", "天猫旗舰店", "上海云端科技有限公司",
            "138****1028", "工业边缘网关 Pro × 3", 3, "8040", CustomerOrder.Status.READY_TO_SHIP,
            "上海一号仓", now.minusMinutes(24), now.plusHours(8)));
        orders.save(order("OMS202607260002", "JD202607265721", "京东自营店", "王先生",
            "139****6612", "温湿度传感器 × 12；通信模组 × 2", 14, "2596", CustomerOrder.Status.ALLOCATING,
            "苏州智能仓", now.minusMinutes(48), now.plusHours(12)));
        orders.save(order("OMS202607260003", "WEB202607260087", "品牌官网商城", "星云数科（上海）有限公司",
            "136****3506", "企业协同软件授权 × 8", 8, "70400", CustomerOrder.Status.PAID,
            "虚拟商品仓", now.minusHours(1), now.plusHours(4)));
        CustomerOrder shipped = order("OMS202607250016", "WEB202607250611", "品牌官网商城", "海岳智能制造有限公司",
            "137****8860", "工业边缘网关 Pro × 20", 20, "53600", CustomerOrder.Status.SHIPPED,
            "上海一号仓", now.minusHours(8), now.minusHours(2));
        shipped.markShipped("顺丰速运", "SF14202607250016");
        orders.save(shipped);
        orders.save(order("OMS202607250009", "OFF202607250039", "华东线下渠道", "嘉禾机电设备有限公司",
            "135****7791", "数字化实施服务包 × 1", 1, "26000", CustomerOrder.Status.AFTER_SALE,
            "服务交付中心", now.minusDays(1), now.minusHours(5)));

        shipments.save(new Shipment("SHP20260726001", "OMS202607260001", "上海一号仓", "顺丰速运",
            null, Shipment.Status.PACKED, 3, null, now.plusDays(1)));
        shipments.save(new Shipment("SHP20260725016", "OMS202607250016", "上海一号仓", "顺丰速运",
            "SF14202607250016", Shipment.Status.SHIPPED, 20, now.minusHours(2), now.plusDays(1)));
        shipments.save(new Shipment("SHP20260726002", "OMS202607260002", "苏州智能仓", "京东物流",
            null, Shipment.Status.EXCEPTION, 14, null, now.plusDays(2)));

        afterSales.save(new AfterSaleRequest("AS20260726001", "OMS202607250009", "嘉禾机电设备有限公司",
            AfterSaleRequest.Type.REFUND_ONLY, "服务排期调整，申请部分退款", money("6800"),
            AfterSaleRequest.Status.PENDING, now.minusMinutes(36), "林运营"));
        afterSales.save(new AfterSaleRequest("AS20260725008", "OMS202607250016", "海岳智能制造有限公司",
            AfterSaleRequest.Type.EXCHANGE, "其中一台设备外壳磕碰", money("2680"),
            AfterSaleRequest.Status.PROCESSING, now.minusHours(5), "周专员"));

        events.save(new OrderEvent("OMS202607260001", "FULFILLMENT", "拣货完成",
            "上海一号仓已完成 3 件商品拣货", "仓储机器人", now.minusMinutes(8)));
        events.save(new OrderEvent("OMS202607260002", "EXCEPTION", "库存分配异常",
            "通信模组可用库存不足，等待跨仓调拨", "OMS 规则引擎", now.minusMinutes(18)));
        events.save(new OrderEvent("OMS202607260003", "PAYMENT", "支付确认",
            "企业网银支付已核销，等待虚拟权益发放", "支付中心", now.minusMinutes(31)));
        events.save(new OrderEvent("OMS202607250016", "LOGISTICS", "包裹已揽收",
            "顺丰速运已揽收，预计明日送达", "物流回传", now.minusHours(2)));
    }

    private CustomerOrder order(String no, String externalNo, String channel, String customer, String phone,
                                String items, int itemCount, String amount, CustomerOrder.Status status,
                                String warehouse, LocalDateTime orderedAt, LocalDateTime promisedShipAt) {
        return new CustomerOrder(no, externalNo, channel, customer, phone, items, itemCount, money(amount),
            money(amount), status, warehouse, orderedAt, promisedShipAt);
    }

    private BigDecimal money(String value) { return new BigDecimal(value).setScale(2); }
}
