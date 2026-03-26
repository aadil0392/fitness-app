package com.fitcoach.food;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final CombinedFoodSearchService combinedFoodSearchService;
    private final IndianFoodService indianFoodService;
    private final OpenFoodFactsService openFoodFactsService;

    public FoodController(CombinedFoodSearchService combinedFoodSearchService,
                          IndianFoodService indianFoodService,
                          OpenFoodFactsService openFoodFactsService) {
        this.combinedFoodSearchService = combinedFoodSearchService;
        this.indianFoodService = indianFoodService;
        this.openFoodFactsService = openFoodFactsService;
    }

    /** Merged search (Indian + Open Food Facts in one response — can be slow). */
    @PostMapping("/search")
    public ResponseEntity<FoodSearchResponse> search(@Valid @RequestBody FoodSearchRequest request) {
        return ResponseEntity.ok(combinedFoodSearchService.search(request.getQuery()));
    }

    /** Instant approximate matches for common Indian dishes (local data). */
    @GetMapping("/indian")
    public ResponseEntity<List<FoodItemDto>> indian(@RequestParam("query") String query) {
        return ResponseEntity.ok(indianFoodService.search(query == null ? "" : query));
    }

    /** Open Food Facts only — often 10–30s; call in parallel from the UI after /indian. */
    @PostMapping("/off")
    public ResponseEntity<List<FoodItemDto>> openFoodFacts(@Valid @RequestBody FoodSearchRequest request) {
        return ResponseEntity.ok(openFoodFactsService.searchRemote(request.getQuery()));
    }
}
