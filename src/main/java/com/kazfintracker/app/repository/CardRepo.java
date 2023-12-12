package com.kazfintracker.app.repository;

import com.kazfintracker.app.model.auth.User;
import com.kazfintracker.app.model.banking.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card, Long> {
    List<Card> findByUserOrderByCreatedDateDesc(User currentUser);

    Optional<Card> findByIdAndUser(Long cardId, User currentUser);
}
