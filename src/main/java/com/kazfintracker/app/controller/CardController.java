package com.kazfintracker.app.controller;

import com.kazfintracker.app.model.banking.Card;
import com.kazfintracker.app.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Card>> getAllCardsForCurrentUser(Authentication authentication) {
        String userEmail = authentication.getName();
        List<Card> cards = cardService.getAllCardsForCurrentUser(userEmail);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCardById(@PathVariable Long cardId, Authentication authentication) {
        String userEmail = authentication.getName();
        Optional<Card> card = cardService.getCardById(cardId, userEmail);

        return card.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCardToCurrentUser(@RequestBody Card card, Authentication authentication) {
        String userEmail = authentication.getName();
        cardService.addCardToCurrentUser(card, userEmail);
        return new ResponseEntity<>("Card added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCard(@RequestBody Card updatedCard, Authentication authentication) {
        String userEmail = authentication.getName();
        cardService.updateCard(updatedCard, userEmail);
        return new ResponseEntity<>("Card updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId, Authentication authentication) {
        String userEmail = authentication.getName();
        cardService.deleteCard(cardId, userEmail);
        return new ResponseEntity<>("Card deleted successfully", HttpStatus.OK);
    }

}
