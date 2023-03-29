package com.inditex.pricesservice.service;

import com.inditex.pricesservice.persistence.Brand;
import com.inditex.pricesservice.persistence.Price;

import java.time.LocalDateTime;

public interface PriceService {

    Price getPrice(LocalDateTime appliedDate, long productId, Brand brand);
}
