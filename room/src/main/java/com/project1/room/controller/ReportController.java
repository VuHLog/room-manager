package com.project1.room.controller;

import com.project1.room.dto.response.ApiResponse;
import com.project1.room.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/count-rooms-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Long> countByRoomStatus(@RequestParam(name = "roomStatus", required = true) String roomStatus) {
        return ApiResponse.<Long>builder()
                .result(reportService.countByRoomStatus(roomStatus))
                .build();
    }

    @GetMapping("/count-branches")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Long> countBranches() {
        return ApiResponse.<Long>builder()
                .result(reportService.countBranches())
                .build();
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Long> revenueCalculation(
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "branchId", required = false, defaultValue = "") String branchId,
            @RequestParam(name = "roomId", required = false, defaultValue = "") String roomId
    ) {
        return ApiResponse.<Long>builder()
                .result(reportService.revenueCalculation(month, year, branchId, roomId))
                .build();
    }
}
