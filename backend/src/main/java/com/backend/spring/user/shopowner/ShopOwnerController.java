package com.backend.spring.user.shopowner;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@CrossOrigin
@RequestMapping("/api/shopOwner")
@RequiredArgsConstructor
public class ShopOwnerController {
    private final ShopOwnerService shopOwnerService;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody ShopOwner shopOwner) {
        if (shopOwnerService.saveShopOwner(shopOwner)) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(UNAUTHORIZED);
    }
}
