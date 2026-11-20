package dev.matheusGama.gerenciamento_pescadores_api.controller;

import dev.matheusGama.gerenciamento_pescadores_api.dto.response.DashboardResponse;
import dev.matheusGama.gerenciamento_pescadores_api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping()
    public ResponseEntity<DashboardResponse> dashboard() {
        DashboardResponse dashboardResponse = dashboardService.dashboard();

        return ResponseEntity.ok(dashboardResponse);
    }

}
