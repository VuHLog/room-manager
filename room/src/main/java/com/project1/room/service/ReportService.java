package com.project1.room.service;

public interface ReportService {
    Long countByRoomStatus(String status);

    Long countBranches();

    Long revenueCalculation(Integer month, Integer year, String branchId, String roomId);
}
