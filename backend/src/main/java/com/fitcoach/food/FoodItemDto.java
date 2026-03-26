package com.fitcoach.food;

public class FoodItemDto {

    private String name;
    private String brand;
    /** Kilocalories per 100 g / 100 ml when available. */
    private Double caloriesPer100g;
    private String quantityHint;
    private String imageUrl;
    private String source = "Open Food Facts";

    public FoodItemDto() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Double getCaloriesPer100g() { return caloriesPer100g; }
    public void setCaloriesPer100g(Double caloriesPer100g) { this.caloriesPer100g = caloriesPer100g; }

    public String getQuantityHint() { return quantityHint; }
    public void setQuantityHint(String quantityHint) { this.quantityHint = quantityHint; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
