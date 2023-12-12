package com.kazfintracker.app.model.banking;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", updatable = false)
    private Long id;

    private BigDecimal amount;

    private TransactionType type;

    private String description;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Transaction() {
    }

    public Transaction(BigDecimal amount, TransactionType type, String description, Card card) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
