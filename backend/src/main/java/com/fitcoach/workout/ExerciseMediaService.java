package com.fitcoach.workout;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Short loop GIFs (start → end of rep) via Giphy CDN — reliable in browsers.
 * Three movement families cover all programmed exercises.
 */
@Service
public class ExerciseMediaService {

    /** Press / push / triceps / curls — upper-body push pattern. */
    private static final String GIF_PUSH =
            "https://media.giphy.com/media/3o7bu3XilJ5BOiSGic/giphy.gif";

    /** Squat / hinge / legs — lower-body pattern. */
    private static final String GIF_LEGS =
            "https://media.giphy.com/media/5xtDarqlsEW6F7F14Fq/giphy.gif";

    /** Pull / row / core / carry — pulling & midline stability. */
    private static final String GIF_PULL_CORE =
            "https://i.giphy.com/3ohzdSrcXh06Fpq0hy.gif";

    private static final String DEFAULT_CUE =
            "Control both directions: smooth start position, strong end position, no rushing.";

    private final Map<String, String> mediaByExactName = new HashMap<>();
    private final Map<String, String> cueByExactName = new HashMap<>();

    public ExerciseMediaService() {
        // Push / press family
        put("Barbell bench press", GIF_PUSH,
                "Bar to mid-chest; elbows ~45°; feet driving down.");
        put("Overhead press", GIF_PUSH,
                "Ribs down; press straight up; biceps beside ears at top.");
        put("Incline DB press", GIF_PUSH,
                "Elbows under wrists; lower with control to chest line.");
        put("Tricep pushdown", GIF_PUSH,
                "Elbows fixed at sides; extend fully without shrugging.");
        put("DB bench press", GIF_PUSH,
                "Stable shoulder blades; dumbbells over elbows at bottom.");
        put("DB overhead press", GIF_PUSH,
                "Brace core; avoid excessive arch in low back.");
        put("Lateral raise", GIF_PUSH,
                "Slight elbow bend; stop where shoulders stay smooth.");
        put("Tricep kickbacks", GIF_PUSH,
                "Upper arm parallel to floor; only forearm moves.");
        put("Push-ups (varied grip)", GIF_PUSH,
                "Straight line head to heels; chest near floor.");
        put("Pike push-ups", GIF_PUSH,
                "Hips high; head travels toward floor between hands.");
        put("Dips (if available)", GIF_PUSH,
                "Shoulders depressed; slight forward lean for chest.");
        put("Diamond push-ups", GIF_PUSH,
                "Hands under sternum; elbows skim ribs.");

        // Pull family
        put("Lat pulldown or pull-ups", GIF_PULL_CORE,
                "Start from full hang; pull elbows to pockets.");
        put("Barbell row", GIF_PULL_CORE,
                "Torso hinged; pull to lower ribs; pause briefly.");
        put("Face pulls", GIF_PULL_CORE,
                "High pull to face; thumbs toward temples at end.");
        put("Barbell curl", GIF_PUSH,
                "Elbows fixed; no hip swing.");
        put("One-arm DB row", GIF_PULL_CORE,
                "Square hips; pull elbow along rib cage.");
        put("DB curls", GIF_PUSH,
                "Full extension each rep; control the lowering.");
        put("Rear-delt flyes", GIF_PULL_CORE,
                "Soft elbows; open arms like hugging a tree.");
        put("Hammer curls", GIF_PUSH,
                "Neutral wrists; no shoulder rock.");
        put("Pull-ups / assisted", GIF_PULL_CORE,
                "Chin over bar; lower with control.");
        put("Inverted rows", GIF_PULL_CORE,
                "Body rigid; touch chest to bar / bar path.");
        put("Bodyweight rows (table)", GIF_PULL_CORE,
                "Same as inverted row; secure anchor.");
        put("Towel curls", GIF_PUSH,
                "Slow eccentric; squeeze handles or towel hard.");

        // Legs / hinge family
        put("Squat or leg press", GIF_LEGS,
                "Knees track toes; chest tall; depth you own.");
        put("Romanian deadlift", GIF_LEGS,
                "Soft knees; push hips back; bar/dumbbells skim legs.");
        put("Leg curl", GIF_LEGS,
                "Control the stretch; squeeze hamstrings at top.");
        put("Calf raise", GIF_LEGS,
                "Pause at top; full stretch at bottom.");
        put("Goblet squat", GIF_LEGS,
                "Elbows inside knees; vertical torso.");
        put("DB RDL", GIF_LEGS,
                "Hinge, not squat; feel hamstrings load.");
        put("Split squat", GIF_LEGS,
                "Most weight on front leg; back knee soft touch.");
        put("Single-leg calf raise", GIF_LEGS,
                "Slow lowering 3 sec.");
        put("Squat", GIF_LEGS,
                "Brace before you descend.");
        put("Lunge", GIF_LEGS,
                "Step length lets front shin stay vertical.");
        put("Glute bridge", GIF_LEGS,
                "Squeeze glutes; don’t hyperextend spine.");
        put("Calf raises", GIF_LEGS,
                "Straight legs for gastroc; slight bend for soleus.");

        put("Bench press", GIF_PUSH,
                "Same as barbell bench if using bar.");
        put("Barbell row", GIF_PULL_CORE,
                "Neutral neck; pull with lats.");
        put("Plank", GIF_PULL_CORE,
                "Forearms or hands; ribs tucked; glutes on.");
        put("DB bench", GIF_PUSH,
                "Wrists stacked over elbows.");
        put("One-arm row", GIF_PULL_CORE,
                "No torso twist at top.");
        put("Push-ups", GIF_PUSH,
                "Hands under shoulders; full ROM.");
        put("Inverted row", GIF_PULL_CORE,
                "Squeeze blades at top.");

        put("Deadlift or RDL", GIF_LEGS,
                "Lats on; bar close; hips and shoulders rise together.");
        put("Lat pulldown", GIF_PULL_CORE,
                "Lean slightly; pull to upper chest.");
        put("Hanging knee raise", GIF_PULL_CORE,
                "Posterior pelvic tilt; slow legs.");
        put("DB OHP", GIF_PUSH,
                "Stand tall; finish biceps beside ears.");
        put("DB row", GIF_PULL_CORE,
                "Supported torso; pull to hip.");
        put("Dead bug", GIF_PULL_CORE,
                "Low back glued down; opposite arm and leg.");
        put("Pike push-up", GIF_PUSH,
                "Shoulder angle like overhead press.");
        put("Bodyweight row", GIF_PULL_CORE,
                "Scapular retraction each rep.");

        put("Core circuit", GIF_PULL_CORE,
                "Plank → side plank → dead bug; quality over speed.");
    }

    private void put(String name, String url, String cue) {
        mediaByExactName.put(name, url);
        cueByExactName.put(name, cue);
    }

    public List<ExerciseDto> toExercises(List<String> names) {
        return names.stream().map(this::forExercise).toList();
    }

    private ExerciseDto forExercise(String name) {
        String url = mediaByExactName.getOrDefault(name, gifByKeywords(name));
        String cue = cueByExactName.getOrDefault(name, keywordCue(name));
        return new ExerciseDto(name, url, cue);
    }

    private String gifByKeywords(String name) {
        String n = name.toLowerCase(Locale.ROOT);
        if (matches(n, "push", "bench", "press", "dip", "tricep", "curl", "fly", "kickback", "lateral", "ohp", "overhead", "incline")) {
            return GIF_PUSH;
        }
        if (matches(n, "pull", "row", "lat", "chin", "face pull", "inverted", "plank", "core", "dead bug", "hanging", "knee raise")) {
            return GIF_PULL_CORE;
        }
        if (matches(n, "squat", "leg", "lunge", "calf", "rdl", "deadlift", "glute", "split", "hinge")) {
            return GIF_LEGS;
        }
        return GIF_PUSH;
    }

    private String keywordCue(String name) {
        String n = name.toLowerCase(Locale.ROOT);
        if (n.contains("squat") || n.contains("leg press")) {
            return "Knees track toes; brace core before each rep.";
        }
        if (n.contains("deadlift") || n.contains("rdl")) {
            return "Neutral spine; hips back; finish tall.";
        }
        if (n.contains("press") || n.contains("bench") || n.contains("push")) {
            return "Stable base; full range without bouncing.";
        }
        if (n.contains("row") || n.contains("pull")) {
            return "Shoulders down; squeeze shoulder blades at end.";
        }
        return DEFAULT_CUE;
    }

    private static boolean matches(String lower, String... keys) {
        for (String k : keys) {
            if (lower.contains(k)) {
                return true;
            }
        }
        return false;
    }
}
