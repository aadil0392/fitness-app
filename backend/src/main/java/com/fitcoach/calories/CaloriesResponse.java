package com.fitcoach.calories;

public class CaloriesResponse {

    private int maintenanceKcal;
    private int suggestedKcal;
    private String note;

    public CaloriesResponse(int maintenanceKcal, int suggestedKcal, String note) {
        this.maintenanceKcal = maintenanceKcal;
        this.suggestedKcal = suggestedKcal;
        this.note = note;
    }

    public int getMaintenanceKcal() { return maintenanceKcal; }
    public void setMaintenanceKcal(int maintenanceKcal) { this.maintenanceKcal = maintenanceKcal; }

    public int getSuggestedKcal() { return suggestedKcal; }
    public void setSuggestedKcal(int suggestedKcal) { this.suggestedKcal = suggestedKcal; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
