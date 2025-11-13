package com.example.educonnect.controller;

import com.example.educonnect.dto.StatisticsResponseDto;
import com.example.educonnect.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticService statisticService;
    @GetMapping
    public ResponseEntity<StatisticsResponseDto> getTenantStatistics(){
        StatisticsResponseDto tenantStatistics = statisticService.getTenantStatistics();
        return ResponseEntity.ok(tenantStatistics);
    }
}
