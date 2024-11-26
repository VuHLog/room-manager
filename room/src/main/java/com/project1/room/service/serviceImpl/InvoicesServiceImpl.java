package com.project1.room.service.serviceImpl;

import com.project1.room.constants.ContractStatus;
import com.project1.room.constants.PaymentStatus;
import com.project1.room.dao.*;
import com.project1.room.dto.request.InvoicesRequest;
import com.project1.room.dto.request.InvoicesStatusRequest;
import com.project1.room.dto.response.InvoicesResponse;
import com.project1.room.entity.*;
import com.project1.room.exception.AppException;
import com.project1.room.exception.ErrorCode;
import com.project1.room.mapper.InvoicesMapper;
import com.project1.room.service.ContractsService;
import com.project1.room.service.InvoicesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final ContractsRepository contractsRepository;

    private final UsersRepository usersRepository;

    private final ContractsService contractsService;

    private final InvoicesMapper invoicesMapper;

    public InvoicesServiceImpl(InvoicesRepository invoicesRepository, RoomsRepository roomsRepository, ServiceRoomsRepository serviceRoomsRepository, ContractsRepository contractsRepository, UsersRepository usersRepository, ContractsService contractsService, InvoicesMapper invoicesMapper) {
        this.invoicesRepository = invoicesRepository;
        this.roomsRepository = roomsRepository;
        this.serviceRoomsRepository = serviceRoomsRepository;
        this.contractsRepository = contractsRepository;
        this.usersRepository = usersRepository;
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
                invoices -> invoices.forEach(invoice -> {
                    if(!invoice.getPaymentStatus().equals(PaymentStatus.CANCELED.getStatus())) {
                        throw new AppException(ErrorCode.INVOICES_EXISTED);
                    }
                })
        );

        List<ServiceRooms> serviceRooms = serviceRoomsRepository.findByRoom_IdAndMonthAndYear(roomId, month, year);
        int amount = 0;
        for(ServiceRooms serviceRoom : serviceRooms) {
            amount += serviceRoom.getPrice() * serviceRoom.getUsage_quantity();
        }

        Rooms room = roomsRepository.findById(roomId).orElse(null);

        Contracts contract = contractsRepository.findByStatusAndRoom_Id(ContractStatus.ENABLED.getStatus(), roomId);
        if(contract == null)
            throw new AppException(ErrorCode.ROOM_WITHOUT_CONTRACT);
        amount += contract.getPrice();

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
        Invoices invoice = invoicesRepository.findById(invoicesId).orElseThrow(null);
        String status = request.getStatus();
        if(!status.equals(PaymentStatus.CANCELED.getStatus())) {
            //get month, year
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month = localDate.getMonthValue();
            int year = localDate.getYear();
            // check invoices status existed
            invoicesRepository.findByRoom_IdAndYearAndMonth(invoice.getRoom().getId(), year, month).ifPresent(
                    invoices -> invoices.forEach(i -> {
                        if(!i.getPaymentStatus().equals(PaymentStatus.CANCELED.getStatus())) {
                            throw new AppException(ErrorCode.INVOICES_EXISTED);
                        }
                    })
            );
        }
        invoice.setPaymentStatus(status);
        return invoicesMapper.toInvoicesResponse(invoicesRepository.save(invoice));
    }

    @Override
    public void deleteInvoices(String invoicesId) {
        invoicesRepository.deleteById(invoicesId);
    }


    public boolean hasManager(String invoicesId) {

        //get current user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);

        //get invoice
        Invoices invoices = invoicesRepository.findById(invoicesId).orElse(null);
        return user != null && invoices != null && user.getId().equals(invoices.getRoom().getBranch().getManager().getId());
    }

    public boolean isCreateForManager(String roomId) {

        //get current user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByUsername(username).orElse(null);

        //get room
        Rooms room = roomsRepository.findById(roomId).orElse(null);

        return user != null && room != null && user.getId().equals(room.getBranch().getManager().getId());
    }
}
