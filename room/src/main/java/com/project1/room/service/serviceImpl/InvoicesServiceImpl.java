package com.project1.room.service.serviceImpl;

import com.project1.room.constants.ContractStatus;
import com.project1.room.constants.PaymentStatus;
import com.project1.room.dao.InvoicesRepository;
import com.project1.room.dao.RoomsRepository;
import com.project1.room.dao.ServiceRoomsRepository;
import com.project1.room.dto.request.InvoicesRequest;
import com.project1.room.dto.request.InvoicesStatusRequest;
import com.project1.room.dto.response.InvoicesResponse;
import com.project1.room.entity.Invoices;
import com.project1.room.entity.Rooms;
import com.project1.room.entity.ServiceRooms;
import com.project1.room.exception.AppException;
import com.project1.room.exception.ErrorCode;
import com.project1.room.mapper.InvoicesMapper;
import com.project1.room.service.ContractsService;
import com.project1.room.service.InvoicesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class InvoicesServiceImpl implements InvoicesService {
    private final InvoicesRepository invoicesRepository;

    private final RoomsRepository roomsRepository;

    private final ServiceRoomsRepository serviceRoomsRepository;

    private final ContractsService contractsService;

    private final InvoicesMapper invoicesMapper;

    public InvoicesServiceImpl(InvoicesRepository invoicesRepository, RoomsRepository roomsRepository, ServiceRoomsRepository serviceRoomsRepository, ContractsService contractsService, InvoicesMapper invoicesMapper) {
        this.invoicesRepository = invoicesRepository;
        this.roomsRepository = roomsRepository;
        this.serviceRoomsRepository = serviceRoomsRepository;
        this.contractsService = contractsService;
        this.invoicesMapper = invoicesMapper;
    }

    @Override
    public Page<InvoicesResponse> getInvoices(String field, Integer pageNumber, Integer pageSize, String sort, String search) {

        Sort sortable = sort.equals("ASC") ? Sort.by(field).ascending() : Sort.by(field).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortable);
        return invoicesRepository.findByOrderByYearDescMonthDesc(pageable).map(invoicesMapper::toInvoicesResponse);
    }

    @Override
    public InvoicesResponse createInvoices(InvoicesRequest request) {
        String roomId = request.getRoomId();

        //check room contract status
        if(contractsService.getContractsByStatusAndRoomId(ContractStatus.ENABLED.getStatus(), roomId).size() != 1) {
            throw new AppException(ErrorCode.ROOM_WITHOUT_CONTRACT);
        }

        String paymentStatus = PaymentStatus.UNPAID.getStatus();

        //get month, year
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        // check invoices status existed
        invoicesRepository.findByRoom_IdAndYearAndMonth(roomId, year, month).ifPresent(
                invoices -> {
                    invoices.forEach(invoice -> {
                        if(!invoice.getPaymentStatus().equals(PaymentStatus.CANCELED.getStatus())) {
                            throw new AppException(ErrorCode.INVOICES_EXISTED);
                        }
                    });
                }
        );

        List<ServiceRooms> serviceRooms = serviceRoomsRepository.findByRoom_IdAndMonthAndYear(roomId, month, year);
        int amount = 0;
        for(ServiceRooms serviceRoom : serviceRooms) {
            amount += serviceRoom.getPrice() * serviceRoom.getUsage_quantity();
        }

        Rooms room = roomsRepository.findById(roomId).orElse(null);
        amount += room.getPrice();

        Invoices invoices = Invoices.builder()
                .amount(amount)
                .year(year)
                .month(month)
                .paymentStatus(paymentStatus)
                .room(room)
                .build();
        return invoicesMapper.toInvoicesResponse(invoicesRepository.save(invoices));
    }

    @Override
    public InvoicesResponse updateInvoicesStatus(String invoicesId, InvoicesStatusRequest request) {
        Invoices invoices = invoicesRepository.findById(invoicesId).orElseThrow(null);
        invoices.setPaymentStatus(request.getStatus());
        return invoicesMapper.toInvoicesResponse(invoicesRepository.save(invoices));
    }

    @Override
    public void deleteInvoices(String invoicesId) {
        invoicesRepository.deleteById(invoicesId);
    }
}
