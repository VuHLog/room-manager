package com.project1.room.service.serviceImpl;

import com.project1.room.constants.PaymentStatus;
import com.project1.room.dao.BranchesRepository;
import com.project1.room.dao.InvoicesRepository;
import com.project1.room.dao.RoomsRepository;
import com.project1.room.dao.specifications.ReportSpecification;
import com.project1.room.entity.Invoices;
import com.project1.room.service.ReportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
    @PersistenceContext
    private EntityManager entityManager;

    private final InvoicesRepository invoicesRepository;

    private final RoomsRepository roomsRepository;

    private final BranchesRepository branchesRepository;

    public ReportServiceImpl(InvoicesRepository invoicesRepository, RoomsRepository roomsRepository, BranchesRepository branchesRepository) {
        this.invoicesRepository = invoicesRepository;
        this.roomsRepository = roomsRepository;
        this.branchesRepository = branchesRepository;
    }

    @Override
    public Long countByRoomStatus(String status) {
        return roomsRepository.countByStatus(status);
    }

    @Override
    public Long countBranches() {
        return branchesRepository.count();
    }

    @Override
    public Long revenueCalculation(Integer month, Integer year, String branchId, String roomId) {
        //filter where
        Specification<Invoices> specs = Specification.where(null);
        if(month != null){
            specs = specs.and(ReportSpecification.hasMonth(month));
        }
        if(year != null){
            specs = specs.and(ReportSpecification.hasYear(year));
        }
        if(branchId != null && !branchId.trim().isEmpty()){
            specs = specs.and(ReportSpecification.hasBranch(branchId));
        }else if(!roomId.trim().isEmpty()){
            specs = specs.and(ReportSpecification.hasRoom(roomId));
        }
        specs = specs.and(ReportSpecification.hasPaymentStatus(PaymentStatus.PAID.getStatus()));

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Invoices> root = cq.from(Invoices.class);

        Predicate predicate = specs.toPredicate(root, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }

        // sum (amount)
        cq.select(cb.sumAsLong(root.get("amount")));

        Long result = entityManager.createQuery(cq).getSingleResult();

        return result != null ? result : 0;
    }
}
