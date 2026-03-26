package com.fitcoach.calories;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CaloriesService {

    private static final Map<String, Double> ACTIVITY_MULTIPLIERS = Map.of(
            "sedentary", 1.2,
            "light", 1.375,
            "moderate", 1.55,
            "active", 1.725,
            "very_active", 1.9
    );

    public CaloriesResponse calculate(CaloriesRequest req) {
        int bmr = bmrMifflinStJeor(
                "male".equalsIgnoreCase(req.getSex()),
                req.getAgeYears(),
                req.getWeightKg(),
                req.getHeightCm()
        );
        double mult = ACTIVITY_MULTIPLIERS.getOrDefault(req.getActivity().toLowerCase(), 1.55);
        int maintenance = (int) Math.round(bmr * mult);

        String goal = req.getGoal() != null ? req.getGoal().toLowerCase() : "maintain";
        int suggested;
        String note;

        if ("lose".equals(goal)) {
            int deficit = (int) Math.round(maintenance * 0.2);
            int minSafe = Math.max(maintenance - 750, 1200);
            suggested = Math.max(maintenance - deficit, minSafe);
            note = "Typical fat-loss range: ~15–25% below maintenance or ~500 kcal deficit. Never go below medical minimums without supervision.";
        } else if ("gain".equals(goal)) {
            int surplus = (int) Math.round(maintenance * 0.1);
            suggested = maintenance + surplus;
            note = "Lean gain: small surplus (~5–15% above maintenance) with adequate protein and training.";
        } else {
            suggested = maintenance;
            note = "Eat at maintenance to keep weight stable.";
        }

        return new CaloriesResponse(maintenance, suggested, note);
    }

    private int bmrMifflinStJeor(boolean male, int ageYears, double weightKg, int heightCm) {
        double base = 10 * weightKg + 6.25 * heightCm - 5 * ageYears;
        return (int) Math.round(male ? base + 5 : base - 161);
    }
}
