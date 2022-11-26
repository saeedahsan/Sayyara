package com.backend.spring.services;

import com.backend.spring.entities.Quote;
import com.backend.spring.entities.Shop;
import com.backend.spring.entities.VehicleOwner;
import com.backend.spring.exceptions.DataNotFoundException;
import com.backend.spring.repositories.ShopRepository;
import com.backend.spring.repositories.VehicleOwnerRepository;
import com.backend.spring.validators.QuoteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleOwnerService {
    private final VehicleOwnerRepository vehicleOwnerRepository;

    private final VehicleOwnerSaveHelper vehicleOwnerSaveHelper;

    private final ShopRepository shopRepository;

    public List<Quote> getAllQuotes(long id) {
        return vehicleOwnerRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Vehicle owner with id " + id + " not found."))
                .getQuotes();
    }

    public Quote createQuote(Quote quote, long shopId) {
        new QuoteValidator(quote).validate();

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new DataNotFoundException("Shop with id " + shopId + " not found."));

        quote.setShop(shop);

        VehicleOwner vehicleOwner = vehicleOwnerRepository
                .findByEmail(quote.getVehicleOwner().getEmail())
                .orElseGet(quote::getVehicleOwner);

        vehicleOwner.getQuotes().add(quote);
        vehicleOwner = vehicleOwnerSaveHelper.save(vehicleOwner);

        return vehicleOwner
                .getQuotes()
                .get(vehicleOwner.getQuotes().size() - 1);
    }
}