package com.thebuyback.eve.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.Collections.singleton;

import com.thebuyback.eve.config.AppraisalService;
import com.thebuyback.eve.domain.Appraisal;
import com.thebuyback.eve.domain.AppraisalFailed;
import com.thebuyback.eve.domain.TypeBuybackRate;
import com.thebuyback.eve.domain.TypeIngredients;
import com.thebuyback.eve.domain.enumeration.TypeCategory;
import com.thebuyback.eve.repository.TypeBuybackRateRepository;
import com.thebuyback.eve.repository.TypeIngredientsRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BuybackRateUpdater {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TypeBuybackRateRepository buybackRateRepository;
    private final TypeIngredientsRepository ingredientsRepository;
    private final TypeService typeService;
    private final AppraisalService appraisalService;

    private final Map<Long, Double> priceCache = new HashMap<>();

    public BuybackRateUpdater(final TypeBuybackRateRepository buybackRateRepository,
                              final TypeIngredientsRepository ingredientsRepository,
                              final TypeService typeService,
                              final AppraisalService appraisalService) {
        this.buybackRateRepository = buybackRateRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.typeService = typeService;
        this.appraisalService = appraisalService;
    }

    @Async
    @Scheduled(fixedDelay = 7_200_000L) // 2 hours
    public void updateBuybackRates() {
        final List<TypeBuybackRate> moonOreRates = buybackRateRepository.findByCategory(TypeCategory.MOON_ORE);
        final Set<Long> typeIds = moonOreRates.stream().map(TypeBuybackRate::getTypeId).collect(Collectors.toSet());

        final List<TypeIngredients> allIngredients = ingredientsRepository.findAllByTypeIdIn(typeIds);
        for (final TypeBuybackRate moonOreRate : moonOreRates) {
            final Long typeId = moonOreRate.getTypeId();
            allIngredients.stream().filter(i -> i.getTypeId() == typeId).findFirst().ifPresent(i -> {
                final Map<Long, Integer> itemIngredients = i.getIngredients();
                final Set<Long> ingredientTypeIds = itemIngredients.entrySet().stream().map(Entry::getKey)
                                                                   .collect(Collectors.toSet());
                try {
                    final Map<Long, Double> ingredientPrices = getPrices(ingredientTypeIds);
                    final List<TypeBuybackRate> ingredientRates = buybackRateRepository.findAllByTypeIdIn(ingredientTypeIds);
                    final Map<Long, Double> ratedIngredientPrices = new HashMap<>();
                    ingredientPrices.entrySet().forEach(entry -> {
                        final Optional<TypeBuybackRate> optionalRate = ingredientRates.stream().filter(rate -> Objects.equals(rate.getTypeId(), entry.getKey())).findFirst();
                        double price = entry.getValue() * (optionalRate.isPresent() ? optionalRate.get().getRate() : 0.9);
                        ratedIngredientPrices.put(entry.getKey(), price);
                    });

                    double totalIngredientsPrice = 0.0;
                    for (Entry<Long, Double> p : ratedIngredientPrices.entrySet()) {
                        final Long ingredientTypeId = p.getKey();
                        final Double ingredientPrice = p.getValue();
                        final Integer ingredientQuantity = itemIngredients.get(ingredientTypeId);
                        totalIngredientsPrice += ingredientQuantity * ingredientPrice;
                    }

                    final Double priceForMoonOre = getPrices(singleton(i.getTypeId())).get(i.getTypeId()) * i.getQuantityToReprocess();
                    final double rate = totalIngredientsPrice / priceForMoonOre;
                    moonOreRate.setRate(rate);
                    buybackRateRepository.save(moonOreRate);
                } catch (AppraisalFailed appraisalFailed) {
                    log.error("Failed to get ingredient appraisal for typeId {}", i.getTypeId(), appraisalFailed);
                }
            });
        }
    }

    private Map<Long, Double> getPrices(final Collection<Long> typeIds) throws AppraisalFailed {
        final Map<Long, Double> result = new HashMap<>();
        final Set<String> typeNames = typeIds.stream().map(id -> {
            if (priceCache.containsKey(id)) {
                result.put(id, priceCache.get(id));
                // don't further resolve this typeId
                return null;
            }
            return typeService.getNameByTypeId(id);
        }).filter(Objects::nonNull).collect(Collectors.toSet());
        final Appraisal appraisal = appraisalService.getAppraisalFromList(typeNames);

        appraisal.getItems().forEach(item -> {
            result.put(item.getTypeID(), item.getJitaBuyPerUnit());
            priceCache.put(item.getTypeID(), item.getJitaBuyPerUnit());
        });
        return result;
    }
}
