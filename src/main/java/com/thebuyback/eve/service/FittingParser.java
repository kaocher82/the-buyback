package com.thebuyback.eve.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.thebuyback.eve.domain.stock.Fitting;
import com.thebuyback.eve.domain.stock.FittingItem;

import org.springframework.stereotype.Component;

@Component
public class FittingParser {

    private final TypeService typeService;

    public FittingParser(final TypeService typeService) {
        this.typeService = typeService;
    }

    public Fitting parse(String text) {
        final AtomicReference<String> fittingName = new AtomicReference<>();
        Map<String, Integer> quantities = new HashMap<>();
        Arrays.stream(text.split("\n")).filter(s -> !s.isEmpty()).forEach(s -> {
            String typeName;
            int quantity = 1;
            if (s.startsWith("[")) {
                if (s.contains("[empty") || s.contains("[Empty")) {
                    return;
                }
                final String[] split = s.split(",");
                typeName = split[0].substring(1).trim();
                final String trimmedName = split[1].trim();
                final String fittingNameString = trimmedName.substring(0, trimmedName.length() - 1);
                fittingName.set(fittingNameString);
            } else if (s.contains(" x")) {
                final String[] split = s.split(" x");
                typeName = split[0];
                quantity = Integer.parseInt(split[1]);
            } else {
                typeName = s;
            }

            if (!quantities.containsKey(typeName)) {
                quantities.put(typeName, 0);
            }
            quantities.put(typeName, quantities.get(typeName) + quantity);
        });

        final List<FittingItem> items = quantities.entrySet().stream().map(e -> {
            final long typeId = typeService.getTypeIdByName(e.getKey());
            return new FittingItem(typeId, e.getValue());
        }).collect(Collectors.toList());

        if (null == fittingName.get()) {
            throw new RuntimeException("Fitting name was null: " + text);
        }

        return new Fitting(fittingName.get(), items);
    }
}
