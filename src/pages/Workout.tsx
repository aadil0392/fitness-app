import { useState } from "react";
import { postWorkoutPlan } from "../api/client";
import type { WorkoutPlanResponseBody } from "../api/client";

type PrimaryGoal = "fat_loss" | "muscle_gain" | "general_fitness" | "strength";
type DaysPerWeek = 2 | 3 | 4 | 5 | 6;
type Equipment = "gym" | "home_dumbbells" | "bodyweight";

const GOALS: { value: PrimaryGoal; label: string }[] = [
  { value: "fat_loss", label: "Fat loss" },
  { value: "muscle_gain", label: "Muscle gain" },
  { value: "general_fitness", label: "General fitness" },
  { value: "strength", label: "Strength" },
];

const DAYS: DaysPerWeek[] = [2, 3, 4, 5, 6];
const EQUIPMENT: { value: Equipment; label: string }[] = [
  { value: "gym", label: "Gym (barbell + machines)" },
  { value: "home_dumbbells", label: "Home + dumbbells" },
  { value: "bodyweight", label: "Bodyweight only" },
];

export default function Workout() {
  const [goal, setGoal] = useState<PrimaryGoal>("general_fitness");
  const [daysPerWeek, setDaysPerWeek] = useState<DaysPerWeek>(4);
  const [equipment, setEquipment] = useState<Equipment>("gym");
  const [experience, setExperience] = useState<"beginner" | "intermediate" | "advanced">(
    "intermediate"
  );
  const [submitted, setSubmitted] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [plan, setPlan] = useState<WorkoutPlanResponseBody | null>(null);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setPlan(null);
    setLoading(true);
    setSubmitted(true);
    try {
      const data = await postWorkoutPlan({
        goal,
        daysPerWeek,
        equipment,
        experience,
      });
      setPlan(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Request failed");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page">
      <div className="container container-wide">
        <h1 style={{ marginBottom: "0.5rem" }}>Workout plan suggestion</h1>
        <p style={{ color: "var(--text-muted)", marginBottom: "1.5rem" }}>
          Tell us your goal, how many days you can train, and what equipment you have.
        </p>

        <form className="card" onSubmit={handleSubmit}>
          <label className="label">Primary goal</label>
          <select
            className="select"
            value={goal}
            onChange={(e) => setGoal(e.target.value as PrimaryGoal)}
          >
            {GOALS.map((g) => (
              <option key={g.value} value={g.value}>
                {g.label}
              </option>
            ))}
          </select>

          <label className="label">Days per week</label>
          <select
            className="select"
            value={daysPerWeek}
            onChange={(e) => setDaysPerWeek(Number(e.target.value) as DaysPerWeek)}
          >
            {DAYS.map((d) => (
              <option key={d} value={d}>
                {d} days
              </option>
            ))}
          </select>

          <label className="label">Equipment</label>
          <select
            className="select"
            value={equipment}
            onChange={(e) => setEquipment(e.target.value as Equipment)}
          >
            {EQUIPMENT.map((eq) => (
              <option key={eq.value} value={eq.value}>
                {eq.label}
              </option>
            ))}
          </select>

          <label className="label">Experience</label>
          <select
            className="select"
            value={experience}
            onChange={(e) => setExperience(e.target.value as typeof experience)}
          >
            <option value="beginner">Beginner</option>
            <option value="intermediate">Intermediate</option>
            <option value="advanced">Advanced</option>
          </select>

          <button
            type="submit"
            className="btn"
            style={{ width: "100%", marginTop: "0.5rem" }}
            disabled={loading}
          >
            {loading ? "Loading plan…" : "Get plan"}
          </button>
        </form>

        {error && (
          <div className="card" style={{ marginTop: "1rem", borderColor: "var(--error, #ef4444)" }}>
            <p style={{ color: "var(--error, #ef4444)", margin: 0 }}>
              {error}. Make sure the backend is running on port 8080.
            </p>
          </div>
        )}

        {submitted && plan && (
          <div className="card" style={{ marginTop: "1.5rem" }}>
            <h2 style={{ marginTop: 0 }}>{plan.title}</h2>
            <p style={{ color: "var(--text-muted)" }}>{plan.summary}</p>
            <p>
              <strong>Split:</strong> {plan.split}
            </p>
            <h3>Suggested days</h3>
            <p className="form-guide-note">
              Each movement uses a <strong>short loop GIF</strong> (start → end of the rep) plus a
              form cue. Stop if anything hurts—this is educational, not a substitute for in-person
              coaching.
            </p>
            {plan.days.map((day, i) => (
              <div key={i} className="workout-day">
                <h4>{day.name}</h4>
                <div className="focus">{day.focus}</div>
                <div className="exercise-grid">
                  {day.exercises.map((ex, j) => (
                    <article key={j} className="exercise-card">
                      <div className="exercise-media">
                        <img
                          src={ex.gifUrl}
                          alt={`Form reference: ${ex.name}`}
                          loading="lazy"
                          width={320}
                          height={200}
                          referrerPolicy="no-referrer"
                          decoding="async"
                        />
                      </div>
                      <div className="exercise-body">
                        <h5 className="exercise-name">{ex.name}</h5>
                        <p className="exercise-cue">{ex.formCue}</p>
                      </div>
                    </article>
                  ))}
                </div>
              </div>
            ))}
            <h3>Tips</h3>
            <ul>
              {plan.tips.map((t, i) => (
                <li key={i}>{t}</li>
              ))}
            </ul>
          </div>
        )}

        <p className="disclaimer">
          Plans are rule-based suggestions for demonstration. Adjust volume and exercises to your
          ability and consult a professional if you have injuries or health conditions.
        </p>
      </div>
    </div>
  );
}
