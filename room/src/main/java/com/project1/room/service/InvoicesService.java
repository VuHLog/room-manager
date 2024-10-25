package com.project1.room.service;

import com.project1.room.dto.request.InvoicesRequest;
import com.project1.room.dto.response.InvoicesResponse;

public interface InvoicesService {
    InvoicesResponse createInvoices(InvoicesRequest request);
}
