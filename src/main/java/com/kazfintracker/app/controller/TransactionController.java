package com.kazfintracker.app.controller;

import com.kazfintracker.app.model.banking.Transaction;
import com.kazfintracker.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Transaction>> getAllTransactions(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Page<Transaction> transactionList = transactionService.getAllTransactions(page, size);
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping("/byCard/{cardId}")
    public ResponseEntity<Page<Transaction>> getTransactionsByCardId(@PathVariable Long cardId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                                     Authentication authentication) {
        String userEmail = authentication.getName();

        Page<Transaction> transactionByCardId = transactionService.getTransactionByCardId(cardId, page, size, userEmail);
        return ResponseEntity.ok(transactionByCardId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transactionById = transactionService.getTransactionById(id);
        return transactionById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction,
                                                      @RequestParam Long cardId,
                                                      Authentication authentication) {
        String userEmail = authentication.getName();

        Transaction added = transactionService.addTransaction(cardId, transaction, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable Long id,
                                                    @RequestParam Long cardId,
                                                    @RequestBody Transaction updatedTransaction,
                                                    Authentication authentication) {
        String userEmail = authentication.getName();

        Optional<Transaction> result = transactionService.updateTransaction(cardId, id, updatedTransaction, userEmail);

        return result.map(value -> new ResponseEntity<>("Transaction updated successfully", HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Transaction not found", HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id,
                                                    @RequestParam Long cardId,
                                                    Authentication authentication) {
        String userEmail = authentication.getName();

        transactionService.deleteTransaction(cardId, id, userEmail);
        return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/countByCard/{cardId}")
    public ResponseEntity<Integer> getTransactionCountByCard(@PathVariable Long cardId) {
        int count = transactionService.getTransactionCountByCard(cardId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
