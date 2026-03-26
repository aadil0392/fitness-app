package com.fitcoach.workout;

public class ExerciseDto {

    private String name;
    /** URL to a GIF or still image demonstrating form (educational media). */
    private String gifUrl;
    /** Short cue for posture / safety. */
    private String formCue;

    public ExerciseDto() {}

    public ExerciseDto(String name, String gifUrl, String formCue) {
        this.name = name;
        this.gifUrl = gifUrl;
        this.formCue = formCue;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGifUrl() { return gifUrl; }
    public void setGifUrl(String gifUrl) { this.gifUrl = gifUrl; }

    public String getFormCue() { return formCue; }
    public void setFormCue(String formCue) { this.formCue = formCue; }
}
