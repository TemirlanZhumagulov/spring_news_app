package com.kazfintracker.app.service;

import com.kazfintracker.app.model.banking.Card;
import com.kazfintracker.app.repository.CardRepo;
import com.kazfintracker.app.model.auth.User;
import com.kazfintracker.app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CardService {
    private final CardRepo cardRepo;
    private final UserRepo userRepo;

    @Autowired
    public CardService(CardRepo cardRepo, UserRepo userRepo) {
        this.cardRepo = cardRepo;
        this.userRepo = userRepo;
    }

    public List<Card> getAllCardsForCurrentUser(String userEmail) {
        return cardRepo.findByUserOrderByCreatedDateDesc(userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("0sUkZw66hl :: User not found")));
    }

    public Optional<Card> getCardById(Long cardId, String userEmail) {
        return cardRepo.findByIdAndUser(cardId, userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("OOWfi6gfUA :: User not found")));
    }

    public void addCardToCurrentUser(Card card, String userEmail) {
        User currentUser = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("ixi5gTuJyO :: User not found"));

        card.setUser(currentUser);
        card.setCreatedDate(LocalDateTime.now());

        cardRepo.save(card);
    }

    public void updateCard(Card updatedCard, String userEmail) {
        User currentUser = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("BDUdtF1sLv :: User not found"));

        cardRepo.findByIdAndUser(updatedCard.getId(), currentUser)
                .ifPresentOrElse(existingCard -> {
                    existingCard.setCardNumber(updatedCard.getCardNumber());
                    existingCard.setType(updatedCard.getType());
                    cardRepo.save(existingCard);
                }, () -> {
                    throw new RuntimeException("BtA05ZCVge :: Card not found for the current user");
                });
    }

    public void deleteCard(Long cardId, String userEmail) {
        User currentUser = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("xW70S31CDd :: User not found"));

        cardRepo.findByIdAndUser(cardId, currentUser)
                .ifPresentOrElse(cardRepo::delete, () -> {
                    throw new RuntimeException("jgz16kLsuE :: Card not found for the current user");
                });
    }


}
