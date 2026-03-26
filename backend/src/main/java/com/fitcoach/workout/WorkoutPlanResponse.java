package com.fitcoach.workout;

import java.util.List;

public class WorkoutPlanResponse {

    private String title;
    private String summary;
    private String split;
    private List<WorkoutDayDto> days;
    private List<String> tips;

    public WorkoutPlanResponse() {}

    public WorkoutPlanResponse(String title, String summary, String split,
                               List<WorkoutDayDto> days, List<String> tips) {
        this.title = title;
        this.summary = summary;
        this.split = split;
        this.days = days;
        this.tips = tips;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getSplit() { return split; }
    public void setSplit(String split) { this.split = split; }

    public List<WorkoutDayDto> getDays() { return days; }
    public void setDays(List<WorkoutDayDto> days) { this.days = days; }

    public List<String> getTips() { return tips; }
    public void setTips(List<String> tips) { this.tips = tips; }
}
