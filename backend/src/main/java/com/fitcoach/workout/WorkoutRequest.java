package com.fitcoach.workout;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class WorkoutRequest {

    @NotNull
    private String goal; // fat_loss, muscle_gain, general_fitness, strength

    @NotNull
    @Min(2)
    @Max(6)
    private Integer daysPerWeek;

    @NotNull
    private String equipment; // gym, home_dumbbells, bodyweight

    private String experience = "intermediate"; // beginner, intermediate, advanced

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    public Integer getDaysPerWeek() { return daysPerWeek; }
    public void setDaysPerWeek(Integer daysPerWeek) { this.daysPerWeek = daysPerWeek; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
}
