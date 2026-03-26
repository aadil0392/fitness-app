package com.fitcoach.food;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Open Food Facts — uses compact search + small page size for faster responses.
 */
@Service
public class OpenFoodFactsService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenFoodFactsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FoodItemDto> searchRemote(String rawQuery) {
        String q = rawQuery == null ? "" : rawQuery.trim();
        if (q.isEmpty()) {
            return List.of();
        }

        List<FoodItemDto> fromV2 = trySearchV2(q);
        if (!fromV2.isEmpty()) {
            return fromV2;
        }
        // Legacy search (sometimes returns hits when v2 filters differ)
        return trySearchLegacy(q);
    }

    /** Open Food Facts API v2 — smaller JSON payload when supported. */
    private List<FoodItemDto> trySearchV2(String q) {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString("https://world.openfoodfacts.org/api/v2/search")
                    .queryParam("search_terms", q)
                    .queryParam("page_size", "5")
                    .queryParam("fields", "product_name,brands,quantity,nutriments,image_front_small_url")
                    .build()
                    .encode()
                    .toUri();

            String body = restTemplate.getForObject(uri, String.class);
            if (body == null || body.isBlank()) {
                return List.of();
            }
            JsonNode root = objectMapper.readTree(body);
            JsonNode products = root.path("products");
            return mapProductsArray(products);
        } catch (Exception e) {
            return List.of();
        }
    }

    private List<FoodItemDto> trySearchLegacy(String q) {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString("https://world.openfoodfacts.org/cgi/search.pl")
                    .queryParam("search_terms", q)
                    .queryParam("search_simple", "1")
                    .queryParam("action", "process")
                    .queryParam("json", "1")
                    .queryParam("page_size", "5")
                    .build()
                    .encode()
                    .toUri();

            String body = restTemplate.getForObject(uri, String.class);
            if (body == null || body.isBlank()) {
                return List.of();
            }
            JsonNode root = objectMapper.readTree(body);
            JsonNode products = root.path("products");
            return mapProductsArray(products);
        } catch (Exception e) {
            return List.of();
        }
    }

    private List<FoodItemDto> mapProductsArray(JsonNode products) {
        if (!products.isArray()) {
            return List.of();
        }
        List<FoodItemDto> out = new ArrayList<>();
        for (JsonNode p : products) {
            FoodItemDto item = mapProduct(p);
            if (item != null && item.getCaloriesPer100g() != null) {
                out.add(item);
            }
        }
        return out;
    }

    private FoodItemDto mapProduct(JsonNode p) {
        String name = text(p, "product_name", "generic_name", "product_name_en");
        if (name == null || name.isBlank()) {
            return null;
        }

        JsonNode n = p.path("nutriments");
        Double kcal = firstDouble(n, "energy-kcal_100g", "energy_kcal_100g");
        if (kcal == null) {
            Double kj = firstDouble(n, "energy-kj_100g", "energy_100g");
            if (kj != null && kj > 0) {
                kcal = kj / 4.184;
            }
        }
        String qtyHint = "Typical values per 100 g / 100 ml where listed.";
        if (kcal == null) {
            kcal = firstDouble(n, "energy-kcal_serving", "energy_kcal_serving");
            if (kcal != null) {
                qtyHint = "Calories shown are per serving as listed on the product (not per 100 g). Check the pack.";
            }
        }

        FoodItemDto dto = new FoodItemDto();
        dto.setName(name.trim());
        String brand = text(p, "brands");
        dto.setBrand(brand != null ? brand.trim() : null);
        dto.setCaloriesPer100g(kcal != null ? round1(kcal) : null);

        String qty = text(p, "quantity");
        if (qty != null && !qty.isBlank()) {
            qtyHint = qtyHint + " Pack: " + qty.trim() + ".";
        }
        dto.setQuantityHint(qtyHint);

        String img = text(p, "image_front_small_url", "image_front_url");
        dto.setImageUrl(img);
        dto.setSource("Open Food Facts");

        return dto;
    }

    private static String text(JsonNode parent, String... fields) {
        for (String f : fields) {
            JsonNode v = parent.path(f);
            if (v.isTextual() && !v.asText().isBlank()) {
                return v.asText();
            }
        }
        return null;
    }

    private static Double firstDouble(JsonNode n, String... fields) {
        for (String f : fields) {
            JsonNode v = n.path(f);
            if (v.isNumber()) {
                double d = v.asDouble();
                if (!Double.isNaN(d) && d > 0) {
                    return d;
                }
            }
            if (v.isTextual()) {
                try {
                    double d = Double.parseDouble(v.asText().replace(',', '.'));
                    if (d > 0) {
                        return d;
                    }
                } catch (NumberFormatException ignored) {
                    // next field
                }
            }
        }
        return null;
    }

    private static double round1(double v) {
        return Math.round(v * 10.0) / 10.0;
    }
}
