package com.project1.room.mapper;

import com.project1.room.dto.response.InvoicesResponse;
import com.project1.room.entity.Invoices;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoicesMapper {
    InvoicesResponse toInvoicesResponse(Invoices invoices);
}
