package com.kazfintracker.app.service;

import com.kazfintracker.app.model.banking.Transaction;
import com.kazfintracker.app.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final CardService cardService;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo, CardService cardService) {
        this.transactionRepo = transactionRepo;
        this.cardService = cardService;
    }

    public Page<Transaction> getAllTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepo.findAllByOrderByCreatedDateDesc(pageable);
    }

    public Page<Transaction> getTransactionByCardId(Long cardId, int page, int size, String userEmail) {
        cardService.getCardById(cardId, userEmail)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepo.findByCardIdOrderByCreatedDateDesc(cardId, pageable);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepo.findById(id);
    }

    public Transaction addTransaction(Long cardId, Transaction transaction, String userEmail) {
        transaction.setCard(cardService.getCardById(cardId, userEmail)
                .orElseThrow(() -> new RuntimeException("Card not found")));
        transaction.setCreatedDate(LocalDateTime.now());
        return transactionRepo.save(transaction);
    }
    public Optional<Transaction> updateTransaction(Long cardId, Long id, Transaction updatedTransaction, String userEmail) {
        cardService.getCardById(cardId, userEmail)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        Optional<Transaction> existingTransaction = transactionRepo.findById(id);

        existingTransaction.ifPresent(existing -> {
            if (!existing.getCard().getId().equals(cardId)) {
                throw new RuntimeException("Transaction does not belong to the specified card");
            }
            existing.setCard(updatedTransaction.getCard());
            existing.setAmount(updatedTransaction.getAmount());
            existing.setType(updatedTransaction.getType());
            existing.setDescription(updatedTransaction.getDescription());
            transactionRepo.save(existing);
        });
        return existingTransaction;
    }

    public void deleteTransaction(Long cardId, Long id, String userEmail) {
        cardService.getCardById(cardId, userEmail)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        transactionRepo.findById(id)
                .filter(transaction -> transaction.getCard().getId().equals(cardId))
                .ifPresentOrElse(transactionRepo::delete,
                        () -> {
                            throw new RuntimeException("Transaction not found or does not belong to the specified card");
                        });
    }


    public int getTransactionCountByCard(Long cardId) {
        return transactionRepo.countByCardId(cardId);
    }

}
