package com.project1.room.service;

import com.project1.room.dto.request.InvoicesRequest;
import com.project1.room.dto.request.InvoicesStatusRequest;
import com.project1.room.dto.response.InvoicesResponse;
import org.springframework.data.domain.Page;

public interface InvoicesService {
    Page<InvoicesResponse> getInvoices(String field, Integer pageNumber, Integer pageSize, String sort, String search, String managerId, String roomId, String branchId, String status);

    InvoicesResponse createInvoices(InvoicesRequest request);

    InvoicesResponse updateInvoicesStatus(String invoicesId, InvoicesStatusRequest request);

    void deleteInvoices(String invoicesId);
}
