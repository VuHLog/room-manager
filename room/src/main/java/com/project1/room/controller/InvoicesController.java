package com.project1.room.controller;

import com.project1.room.dto.request.InvoicesRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.InvoicesResponse;
import com.project1.room.service.InvoicesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoicesController {
    private final InvoicesService invoicesService;

    public InvoicesController(InvoicesService invoicesService) {
        this.invoicesService = invoicesService;
    }

    @PostMapping("")
    public ApiResponse<InvoicesResponse> createInvoices(@RequestBody InvoicesRequest request){
        return ApiResponse.<InvoicesResponse>builder()
                .result(invoicesService.createInvoices(request))
                .build();
    }
}
