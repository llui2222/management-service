package com.tpam.service.management.web.controller;

import com.tpam.service.management.messaging.payload.Strategy;
import com.tpam.service.management.service.ManagementService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO This if for testing only
 *
 */
@RestController
@RequestMapping("/management")
public class ManagementController {

    private final ManagementService managementService;

    public ManagementController(final ManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping("/strategies/close-positions")
    public void closeStrategyPositions(@RequestBody final Strategy strategy) {
        managementService.closeStrategyPositions(strategy);
    }
}
