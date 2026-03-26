package com.fitcoach.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndianFoodEntry {

    private List<String> keywords;
    private String displayName;
    private double kcalPer100g;
    private String servingNote;

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public double getKcalPer100g() { return kcalPer100g; }
    public void setKcalPer100g(double kcalPer100g) { this.kcalPer100g = kcalPer100g; }

    public String getServingNote() { return servingNote; }
    public void setServingNote(String servingNote) { this.servingNote = servingNote; }
}
