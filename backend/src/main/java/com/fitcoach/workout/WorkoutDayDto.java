package com.fitcoach.workout;

import java.util.List;

public class WorkoutDayDto {

    private String name;
    private String focus;
    private List<ExerciseDto> exercises;

    public WorkoutDayDto() {}

    public WorkoutDayDto(String name, String focus, List<ExerciseDto> exercises) {
        this.name = name;
        this.focus = focus;
        this.exercises = exercises;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFocus() { return focus; }
    public void setFocus(String focus) { this.focus = focus; }

    public List<ExerciseDto> getExercises() { return exercises; }
    public void setExercises(List<ExerciseDto> exercises) { this.exercises = exercises; }
}
