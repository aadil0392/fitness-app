package com.fitcoach.workout;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkoutPlanService {

    private final ExerciseMediaService exerciseMedia;

    public WorkoutPlanService(ExerciseMediaService exerciseMedia) {
        this.exerciseMedia = exerciseMedia;
    }

    private List<ExerciseDto> ex(List<String> names) {
        return exerciseMedia.toExercises(names);
    }

    private static final List<String> PUSH_GYM_LIST = List.of(
            "Barbell bench press", "Overhead press", "Incline DB press", "Tricep pushdown");
    private static final List<String> PUSH_DB_LIST = List.of(
            "DB bench press", "DB overhead press", "Lateral raise", "Tricep kickbacks");
    private static final List<String> PUSH_BW_LIST = List.of(
            "Push-ups (varied grip)", "Pike push-ups", "Dips (if available)", "Diamond push-ups");

    private static final List<String> PULL_GYM_LIST = List.of(
            "Lat pulldown or pull-ups", "Barbell row", "Face pulls", "Barbell curl");
    private static final List<String> PULL_DB_LIST = List.of(
            "One-arm DB row", "DB curls", "Rear-delt flyes", "Hammer curls");
    private static final List<String> PULL_BW_LIST = List.of(
            "Pull-ups / assisted", "Inverted rows", "Bodyweight rows (table)", "Towel curls");

    private static final List<String> LEG_GYM_LIST = List.of(
            "Squat or leg press", "Romanian deadlift", "Leg curl", "Calf raise");
    private static final List<String> LEG_DB_LIST = List.of(
            "Goblet squat", "DB RDL", "Split squat", "Single-leg calf raise");
    private static final List<String> LEG_BW_LIST = List.of(
            "Squat", "Lunge", "Glute bridge", "Calf raises");

    private static final List<String> FULL_A_GYM = List.of("Squat", "Bench press", "Barbell row", "Plank");
    private static final List<String> FULL_A_DB = List.of("Goblet squat", "DB bench", "One-arm row", "Plank");
    private static final List<String> FULL_A_BW = List.of("Squat", "Push-ups", "Inverted row", "Plank");

    private static final List<String> FULL_B_GYM = List.of("Deadlift or RDL", "Overhead press", "Lat pulldown", "Hanging knee raise");
    private static final List<String> FULL_B_DB = List.of("DB RDL", "DB OHP", "DB row", "Dead bug");
    private static final List<String> FULL_B_BW = List.of("Lunge", "Pike push-up", "Bodyweight row", "Dead bug");

    private List<String> push(String equipment) {
        return switch (equipment) {
            case "gym" -> PUSH_GYM_LIST;
            case "home_dumbbells" -> PUSH_DB_LIST;
            default -> PUSH_BW_LIST;
        };
    }

    private List<String> pull(String equipment) {
        return switch (equipment) {
            case "gym" -> PULL_GYM_LIST;
            case "home_dumbbells" -> PULL_DB_LIST;
            default -> PULL_BW_LIST;
        };
    }

    private List<String> legs(String equipment) {
        return switch (equipment) {
            case "gym" -> LEG_GYM_LIST;
            case "home_dumbbells" -> LEG_DB_LIST;
            default -> LEG_BW_LIST;
        };
    }

    private List<String> fullA(String equipment) {
        return switch (equipment) {
            case "gym" -> FULL_A_GYM;
            case "home_dumbbells" -> FULL_A_DB;
            default -> FULL_A_BW;
        };
    }

    private List<String> fullB(String equipment) {
        return switch (equipment) {
            case "gym" -> FULL_B_GYM;
            case "home_dumbbells" -> FULL_B_DB;
            default -> FULL_B_BW;
        };
    }

    private List<String> baseTips(String goal) {
        List<String> common = List.of(
                "Warm up 5–10 min before each session.",
                "Progress by adding weight or reps when you can complete all sets at the top of the range.",
                "Prioritize sleep and protein (roughly 1.6–2.2 g/kg/day if building muscle)."
        );
        if ("fat_loss".equals(goal)) {
            return new ArrayList<>(common) {{
                add("Fat loss is driven by a calorie deficit; training preserves muscle.");
                add("Optional: add low-intensity cardio on off days if recovery allows.");
            }};
        }
        if ("muscle_gain".equals(goal) || "strength".equals(goal)) {
            return new ArrayList<>(common) {{
                add("Use compound lifts first when energy is highest.");
                add("Deload or reduce volume if joints or motivation suffer for 2+ weeks.");
            }};
        }
        return new ArrayList<>(common);
    }

    public WorkoutPlanResponse suggest(WorkoutRequest req) {
        String goal = req.getGoal() != null ? req.getGoal() : "general_fitness";
        int days = req.getDaysPerWeek() != null ? req.getDaysPerWeek() : 4;
        String equipment = req.getEquipment() != null ? req.getEquipment() : "gym";
        String experience = req.getExperience() != null ? req.getExperience() : "intermediate";

        List<String> tips = baseTips(goal);

        if (days <= 3) {
            List<WorkoutDayDto> dayList = new ArrayList<>();
            dayList.add(new WorkoutDayDto("Day 1 — Full body A", "Compound emphasis", ex(fullA(equipment))));
            dayList.add(new WorkoutDayDto("Day 2 — Full body B", "Posterior + push", ex(fullB(equipment))));
            if (days == 3) {
                List<String> legEx = legs(equipment);
                List<String> day3Ex = new ArrayList<>(legEx.subList(0, 2));
                day3Ex.add(push(equipment).get(0));
                day3Ex.add(pull(equipment).get(0));
                dayList.add(new WorkoutDayDto("Day 3 — Full body C", "Weak points + core", ex(day3Ex)));
            }
            String summary = "fat_loss".equals(goal)
                    ? "Full-body training 2–3×/week fits busy schedules and burns calories while preserving muscle."
                    : "Full-body 2–3×/week is ideal for beginners and for maximizing recovery per muscle group.";
            return new WorkoutPlanResponse(
                    "Full-body split",
                    summary,
                    days + " full-body sessions per week",
                    dayList,
                    tips
            );
        }

        List<String> upperPush = push(equipment);
        List<String> upperPull = pull(equipment);
        List<String> lower = legs(equipment);

        if (days == 4) {
            List<String> lowerCore = new ArrayList<>(lower.subList(0, 3));
            lowerCore.add("Core circuit");
            return new WorkoutPlanResponse(
                    "Upper / Lower split",
                    "Four days allows two upper and two lower sessions—good volume without daily training.",
                    "Upper / Lower × 2 each week",
                    List.of(
                            new WorkoutDayDto("Day 1 — Upper (push bias)", "Chest, shoulders, triceps", ex(upperPush)),
                            new WorkoutDayDto("Day 2 — Lower", "Quads, hamstrings, calves", ex(lower)),
                            new WorkoutDayDto("Day 3 — Upper (pull bias)", "Back, biceps, rear delts", ex(upperPull)),
                            new WorkoutDayDto("Day 4 — Lower + core", "Legs and core", ex(lowerCore))
                    ),
                    tips
            );
        }

        List<WorkoutDayDto> pplDays = List.of(
                new WorkoutDayDto("Push", "Chest, shoulders, triceps", ex(upperPush)),
                new WorkoutDayDto("Pull", "Back, biceps", ex(upperPull)),
                new WorkoutDayDto("Legs", "Lower body", ex(lower))
        );
        List<WorkoutDayDto> allDays = new ArrayList<>(pplDays);
        if (days >= 6) {
            allDays.addAll(pplDays);
        }
        String title = "advanced".equals(experience) ? "Push / Pull / Legs (high frequency)" : "Push / Pull / Legs";
        String summary = "muscle_gain".equals(goal)
                ? "Higher frequency targets each muscle 2×/week when recovery allows—good for hypertrophy."
                : "PPL spreads volume across the week; add cardio or steps on off days if fat loss is the priority.";
        String split = days == 5 ? "PPL + rest or light cardio" : "PPL × 2 (6 days)";

        return new WorkoutPlanResponse(title, summary, split, allDays, tips);
    }
}
