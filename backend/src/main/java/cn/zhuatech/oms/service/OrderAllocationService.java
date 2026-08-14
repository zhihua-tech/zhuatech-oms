/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oms.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderAllocationService {
    public Result allocate(Request request) {
        List<NodeScore> ranked = request.nodes().stream()
            .map(node -> new NodeScore(node.nodeCode(), node.availableStock(),
                node.leadHours() * 3.0 + node.distanceKm() / 50.0 + (100 - node.capacityRate()) * 0.4))
            .sorted(Comparator.comparingDouble(NodeScore::costScore)).toList();
        var fullNode = ranked.stream().filter(node -> node.availableStock() >= request.quantity()).findFirst();
        if (fullNode.isPresent()) {
            NodeScore chosen = fullNode.get();
            return new Result(request.orderNo(), "SINGLE_NODE", List.of(chosen.nodeCode()),
                request.quantity(), 0, chosen.costScore(), List.of("锁定推荐节点库存并生成单节点履约任务"));
        }

        int remaining = request.quantity();
        List<String> selected = new ArrayList<>();
        for (NodeScore node : ranked) {
            if (node.availableStock() <= 0 || selected.size() >= request.maxSplitNodes()) continue;
            selected.add(node.nodeCode());
            remaining -= Math.min(remaining, node.availableStock());
            if (remaining <= 0) break;
        }
        if (remaining <= 0) {
            return new Result(request.orderNo(), "SPLIT", selected, request.quantity(), 0,
                ranked.stream().filter(node -> selected.contains(node.nodeCode())).mapToDouble(NodeScore::costScore).average().orElse(0),
                List.of("按推荐节点拆单并统一回写包裹追踪关系", "校验多包裹承诺时间与运费上限"));
        }
        return new Result(request.orderNo(), "BACKORDER", selected, request.quantity() - remaining, remaining,
            0, List.of("创建缺货预占并通知客服确认延期或替代商品"));
    }

    public record Request(@NotBlank String orderNo, @Min(1) int quantity,
                          @Min(1) @Max(5) int maxSplitNodes,
                          @NotEmpty List<@Valid NodeCandidate> nodes) {}
    public record NodeCandidate(@NotBlank String nodeCode, @Min(0) int availableStock,
                                @Min(0) int leadHours, @Min(0) int distanceKm,
                                @Min(0) @Max(100) int capacityRate) {}
    private record NodeScore(String nodeCode, int availableStock, double costScore) {}
    public record Result(String orderNo, String decision, List<String> selectedNodes,
                         int allocatedQuantity, int backorderQuantity, double allocationScore,
                         List<String> actions) {}
}
