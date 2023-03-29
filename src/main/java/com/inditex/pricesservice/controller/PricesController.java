package com.inditex.pricesservice.controller;

import com.inditex.pricesservice.persistence.Brand;
import com.inditex.pricesservice.persistence.Price;
import com.inditex.pricesservice.service.PriceService;
import com.inditex.pricesservice.service.impl.PriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1")
public class PricesController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/prices")
    public ResponseEntity<?> getPrice(@RequestParam
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime appliedDate,
                                          @RequestParam long productId, @RequestParam long brandId) {
        try {
            Brand brand = Brand.builder()
                    .id(brandId)
                    .build();
            Price price = priceService.getPrice(appliedDate, productId, brand);
            if (price != null) {
                return ResponseEntity.ok(price);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
}
