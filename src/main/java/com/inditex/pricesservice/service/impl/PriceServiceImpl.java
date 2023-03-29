package com.inditex.pricesservice.service.impl;

import com.inditex.pricesservice.persistence.Brand;
import com.inditex.pricesservice.persistence.Price;
import com.inditex.pricesservice.persistence.PriceRepository;
import com.inditex.pricesservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public Price getPrice(LocalDateTime appliedDate, long productId, Brand brand) {
        return priceRepository
                .findFirstByStartDateIsBeforeAndEndDateIsAfterAndProductIdAndBrandIdOrderByPriorityDesc(
                        appliedDate, appliedDate, productId, brand);
    }
}
