package com.thebuyback.eve.service;

import java.util.List;

import com.thebuyback.eve.domain.ItemBuybackRate;
import com.thebuyback.eve.repository.ItemBuybackRateRepository;

import org.springframework.stereotype.Service;

@Service
public class ItemBuybackRateService {

    private final ItemBuybackRateRepository itemRateRepository;
    private final JsonRequestService requestService;

    public ItemBuybackRateService(final ItemBuybackRateRepository itemRateRepository,
                                  final JsonRequestService requestService) {
        this.itemRateRepository = itemRateRepository;
        this.requestService = requestService;
    }

    public List<ItemBuybackRate> getRates(final List<Long> typeIds) {
        return itemRateRepository.findAllByTypeIdIn(typeIds);
    }
}
