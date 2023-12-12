package com.kazfintracker.app.repository;

import com.kazfintracker.app.model.banking.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByOrderByCreatedDateDesc(Pageable pageable);
    Page<Transaction> findByCardIdOrderByCreatedDateDesc(Long sourceId, Pageable pageable);

    int countByCardId(Long sourceId);
}
