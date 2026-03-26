package com.fitcoach.food;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Fast local lookup for common Indian dishes (approximate kcal/100 g from standard references).
 * Shown first in search results alongside Open Food Facts.
 */
@Service
public class IndianFoodService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<IndianFoodEntry> entries = List.of();

    @PostConstruct
    public void load() {
        try (InputStream in = new ClassPathResource("data/indian-foods.json").getInputStream()) {
            entries = objectMapper.readValue(in, new TypeReference<>() {});
        } catch (Exception e) {
            entries = List.of();
        }
    }

    public List<FoodItemDto> search(String rawQuery) {
        String q = rawQuery == null ? "" : rawQuery.trim().toLowerCase(Locale.ROOT);
        if (q.isEmpty()) {
            return List.of();
        }
        List<FoodItemDto> out = new ArrayList<>();
        for (IndianFoodEntry e : entries) {
            if (e.getKeywords() == null) {
                continue;
            }
            for (String kw : e.getKeywords()) {
                if (kw != null && !kw.isBlank() && keywordMatchesQuery(q, kw.toLowerCase(Locale.ROOT))) {
                    out.add(toDto(e));
                    break;
                }
            }
        }
        return out;
    }

    /**
     * Avoids false positives (e.g. "butter" matching "peanut butter") for short tokens.
     */
    private static boolean keywordMatchesQuery(String queryLower, String keywordLower) {
        if (keywordLower.length() >= 5) {
            return queryLower.contains(keywordLower);
        }
        return Pattern.compile("\\b" + Pattern.quote(keywordLower) + "\\b").matcher(queryLower).find();
    }

    private static FoodItemDto toDto(IndianFoodEntry e) {
        FoodItemDto dto = new FoodItemDto();
        dto.setName(e.getDisplayName());
        dto.setBrand("Indian staples (approx.)");
        dto.setCaloriesPer100g(Math.round(e.getKcalPer100g() * 10.0) / 10.0);
        dto.setQuantityHint(e.getServingNote() + " Figures are rounded guides—not lab tested.");
        dto.setImageUrl(null);
        dto.setSource("FitCoach Indian foods guide");
        return dto;
    }
}
