package com.thebuyback.eve.service;

import java.util.List;
import java.util.Set;

import com.thebuyback.eve.domain.TypeBuybackRate;
import com.thebuyback.eve.repository.TypeBuybackRateRepository;

import org.springframework.stereotype.Service;

@Service
public class ItemBuybackRateService {

    private final TypeBuybackRateRepository rateRepository;

    public ItemBuybackRateService(final TypeBuybackRateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public List<TypeBuybackRate> getRates(final Set<Long> typeIds) {
        return rateRepository.findAllByTypeIdIn(typeIds);
    }
}
