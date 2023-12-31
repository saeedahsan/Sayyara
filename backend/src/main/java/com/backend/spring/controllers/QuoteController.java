package com.backend.spring.controllers;

import com.backend.spring.entities.Quote;
import com.backend.spring.exceptions.InvalidDataException;
import com.backend.spring.services.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin
@RequestMapping(path = "quotes")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @GetMapping
    public ResponseEntity<List<Quote>> getAllQuotes(@RequestHeader(AUTHORIZATION) String authorizationHeader) {
        return ResponseEntity.ok(quoteService.getAllQuotes(authorizationHeader));
    }

    @GetMapping(path = "{quoteId}")
    public ResponseEntity<Quote> getQuote(@PathVariable long quoteId) {
        return ResponseEntity.ok(quoteService.getQuote(quoteId));
    }

    @DeleteMapping(path = "{quoteId}")
    public void deleteQuote(@PathVariable long quoteId) {
        quoteService.deleteQuote(quoteId);
    }

    @PatchMapping(path = "{quoteId}")
    public ResponseEntity<Quote> updateQuote(@PathVariable long quoteId,
                                             @RequestBody QuoteInfo quoteInfo) {
        if (quoteInfo.price == null && quoteInfo.status == null)
            return ResponseEntity.noContent().build();
        if (quoteInfo.price == null)
            return ResponseEntity.ok(quoteService.updateQuoteStatus(quoteId, quoteInfo.status));
        if (quoteInfo.status == null)
            return ResponseEntity.ok(quoteService.updateQuotePrice(quoteId, quoteInfo.price));
        throw new InvalidDataException("Invalid data. Must provide either price or status.");
    }

    private record QuoteInfo(String status, Double price){}
}
