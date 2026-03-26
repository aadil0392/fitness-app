package com.fitcoach.food;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FoodSearchRequest {

    @NotBlank
    @Size(max = 200)
    private String query;

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
}
