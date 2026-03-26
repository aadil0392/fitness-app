package com.fitcoach.food;

import java.util.List;

public class FoodSearchResponse {

    private List<FoodItemDto> results;
    private String attribution;

    public FoodSearchResponse() {}

    public FoodSearchResponse(List<FoodItemDto> results, String attribution) {
        this.results = results;
        this.attribution = attribution;
    }

    public List<FoodItemDto> getResults() { return results; }
    public void setResults(List<FoodItemDto> results) { this.results = results; }

    public String getAttribution() { return attribution; }
    public void setAttribution(String attribution) { this.attribution = attribution; }
}
