package com.fitcoach.food;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class CombinedFoodSearchService {

    private static final String ATTRIBUTION =
            "Indian items: approximate values for common home / restaurant portions (guides only). "
                    + "Packaged foods: Open Food Facts (world database). Always verify with labels.";

    private final IndianFoodService indianFoodService;
    private final OpenFoodFactsService openFoodFactsService;

    public CombinedFoodSearchService(IndianFoodService indianFoodService,
                                     OpenFoodFactsService openFoodFactsService) {
        this.indianFoodService = indianFoodService;
        this.openFoodFactsService = openFoodFactsService;
    }

    public FoodSearchResponse search(String query) {
        // Start Open Food Facts on a worker thread while Indian lookup runs (instant).
        CompletableFuture<List<FoodItemDto>> offFuture = CompletableFuture.supplyAsync(
                () -> openFoodFactsService.searchRemote(query));

        List<FoodItemDto> indian = indianFoodService.search(query);
        List<FoodItemDto> off;
        try {
            off = offFuture.get(28, TimeUnit.SECONDS);
        } catch (Exception e) {
            offFuture.cancel(true);
            off = List.of();
        }

        List<FoodItemDto> merged = new ArrayList<>();
        LinkedHashSet<String> seen = new LinkedHashSet<>();

        for (FoodItemDto item : indian) {
            String key = normalize(item.getName());
            if (!seen.contains(key)) {
                seen.add(key);
                merged.add(item);
            }
        }
        for (FoodItemDto item : off) {
            String key = normalize(item.getName());
            if (!seen.contains(key)) {
                seen.add(key);
                merged.add(item);
            }
        }

        String attr = ATTRIBUTION;
        if (off.isEmpty() && !indian.isEmpty()) {
            attr += " Open Food Facts had no hits or timed out—Indian guides shown.";
        }
        return new FoodSearchResponse(merged, attr);
    }

    private static String normalize(String name) {
        if (name == null) {
            return "";
        }
        return name.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ").trim();
    }
}
