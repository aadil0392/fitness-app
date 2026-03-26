package com.fitcoach.calories;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CaloriesRequest {

    @NotNull
    private String sex; // "male" | "female"

    @NotNull
    @Min(15)
    @Max(100)
    private Integer ageYears;

    @NotNull
    @Min(30)
    @Max(300)
    private Double weightKg;

    @NotNull
    @Min(120)
    @Max(230)
    private Integer heightCm;

    @NotNull
    private String activity; // sedentary, light, moderate, active, very_active

    private String goal = "maintain"; // lose, maintain, gain

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public Integer getAgeYears() { return ageYears; }
    public void setAgeYears(Integer ageYears) { this.ageYears = ageYears; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public Integer getHeightCm() { return heightCm; }
    public void setHeightCm(Integer heightCm) { this.heightCm = heightCm; }

    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
}
