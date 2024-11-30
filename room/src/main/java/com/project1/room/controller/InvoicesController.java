package com.project1.room.controller;

import com.project1.room.dto.request.InvoicesRequest;
import com.project1.room.dto.request.InvoicesStatusRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.InvoicesResponse;
import com.project1.room.service.InvoicesService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoicesController {
    private final InvoicesService invoicesService;

    public InvoicesController(InvoicesService invoicesService) {
        this.invoicesService = invoicesService;
    }

    @GetMapping("")
    public Page<InvoicesResponse> getInvoices(
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "managerId", required = false, defaultValue = "") String managerId
    ){
        return invoicesService.getInvoices(field, pageNumber, pageSize, sort, search, managerId);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @invoicesServiceImpl.isCreateForManager(#request.roomId))")
    public ApiResponse<InvoicesResponse> createInvoices(@RequestBody InvoicesRequest request){
        return ApiResponse.<InvoicesResponse>builder()
                .result(invoicesService.createInvoices(request))
                .build();
    }

    @PutMapping("/{invoicesId}/status")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @invoicesServiceImpl.hasManager(#invoicesId))")
    public ApiResponse<InvoicesResponse> updateInvoicesStatus(@PathVariable String invoicesId, @RequestBody InvoicesStatusRequest request){
        return ApiResponse.<InvoicesResponse>builder()
                .result(invoicesService.updateInvoicesStatus(invoicesId, request))
                .build();
    }

    @DeleteMapping("/{invoicesId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @invoicesServiceImpl.hasManager(#invoicesId))")
    public ApiResponse<String> deleteInvoicesId(@PathVariable String invoicesId){
        invoicesService.deleteInvoices(invoicesId);
        return ApiResponse.<String>builder()
                .result("Invoices has been deleted")
                .build();
    }
}
