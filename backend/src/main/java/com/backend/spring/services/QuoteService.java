package com.backend.spring.services;

import com.backend.spring.entities.Quote;
import com.backend.spring.exceptions.DataNotFoundException;
import com.backend.spring.repositories.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteRepository repository;

    private final ShopOwnerRetriever shopOwnerRetriever;

    public List<Quote> getAllQuotes(String authorization) {
        return shopOwnerRetriever.getShop(authorization).getQuotes();
    }

    public Quote getQuote(long id) {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("Quote with id " + id + " not found."));
    }

    public Quote createQuote(Quote quote) {
        return repository.save(quote);
    }

    public void deleteQuote(long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void updateQuote(long id, Double price, LocalDateTime expiryDate) {
        Quote quote = repository.findById(id).orElseThrow(IllegalStateException::new);

        if (price != null) {
            quote.setPrice(price);
        }

        if (expiryDate != null) {
            quote.setExpiryTime(expiryDate);
        }
    }
}