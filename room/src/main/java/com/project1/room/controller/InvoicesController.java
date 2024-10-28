package com.project1.room.controller;

import com.project1.room.dto.request.InvoicesRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.InvoicesResponse;
import com.project1.room.service.InvoicesService;
import org.springframework.data.domain.Page;
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
            @RequestParam(name = "search", required = false, defaultValue = "") String search
    ){
        return invoicesService.getInvoices(field, pageNumber, pageSize, sort, search);
    }

    @PostMapping("")
    public ApiResponse<InvoicesResponse> createInvoices(@RequestBody InvoicesRequest request){
        return ApiResponse.<InvoicesResponse>builder()
                .result(invoicesService.createInvoices(request))
                .build();
    }

    @DeleteMapping("/{invoicesId}")
    public ApiResponse<String> deleteInvoicesId(@PathVariable String invoicesId){
        invoicesService.deleteInvoices(invoicesId);
        return ApiResponse.<String>builder()
                .result("Invoices has been deleted")
                .build();
    }
}
