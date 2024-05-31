package com.company.controller;

import com.company.service.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@Slf4j
@Tag(name = "Statistics")
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;



}
