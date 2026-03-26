import { useState } from "react";
import { ACTIVITY_LABELS, type ActivityLevel } from "../lib/calories";
import { postCalories } from "../api/client";

const ACTIVITY_OPTIONS = Object.entries(ACTIVITY_LABELS) as [ActivityLevel, string][];

export default function Calories() {
  const [sex, setSex] = useState<"male" | "female">("male");
  const [age, setAge] = useState(30);
  const [weightKg, setWeightKg] = useState(75);
  const [heightCm, setHeightCm] = useState(175);
  const [activity, setActivity] = useState<ActivityLevel>("moderate");
  const [goal, setGoal] = useState<"lose" | "maintain" | "gain">("maintain");
  const [submitted, setSubmitted] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [result, setResult] = useState<{
    maintenanceKcal: number;
    suggestedKcal: number;
    note: string;
  } | null>(null);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setResult(null);
    setLoading(true);
    setSubmitted(true);
    try {
      const data = await postCalories({
        sex,
        ageYears: age,
        weightKg,
        heightCm,
        activity,
        goal,
      });
      setResult(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Request failed");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page">
      <div className="container">
        <h1 style={{ marginBottom: "0.5rem" }}>Maintenance calories</h1>
        <p style={{ color: "var(--text-muted)", marginBottom: "1.5rem" }}>
          Answer a few questions to estimate your TDEE (maintenance calories per day).
        </p>

        <form className="card" onSubmit={handleSubmit}>
          <label className="label">Sex (for BMR formula)</label>
          <select
            className="select"
            value={sex}
            onChange={(e) => setSex(e.target.value as "male" | "female")}
          >
            <option value="male">Male</option>
            <option value="female">Female</option>
          </select>

          <label className="label">Age (years)</label>
          <input
            className="input"
            type="number"
            min={15}
            max={100}
            value={age}
            onChange={(e) => setAge(Number(e.target.value))}
          />

          <label className="label">Weight (kg)</label>
          <input
            className="input"
            type="number"
            min={30}
            max={300}
            step={0.1}
            value={weightKg}
            onChange={(e) => setWeightKg(Number(e.target.value))}
          />

          <label className="label">Height (cm)</label>
          <input
            className="input"
            type="number"
            min={120}
            max={230}
            value={heightCm}
            onChange={(e) => setHeightCm(Number(e.target.value))}
          />

          <label className="label">Activity level</label>
          <select
            className="select"
            value={activity}
            onChange={(e) => setActivity(e.target.value as ActivityLevel)}
          >
            {ACTIVITY_OPTIONS.map(([key, label]) => (
              <option key={key} value={key}>
                {label}
              </option>
            ))}
          </select>

          <label className="label">Goal (optional — adjusts suggested calories)</label>
          <select
            className="select"
            value={goal}
            onChange={(e) => setGoal(e.target.value as typeof goal)}
          >
            <option value="maintain">Maintain weight</option>
            <option value="lose">Fat loss (deficit)</option>
            <option value="gain">Muscle gain (surplus)</option>
          </select>

          <button
            type="submit"
            className="btn"
            style={{ width: "100%", marginTop: "0.5rem" }}
            disabled={loading}
          >
            {loading ? "Calculating…" : "Calculate"}
          </button>
        </form>

        {error && (
          <div className="card" style={{ marginTop: "1rem", borderColor: "var(--error, #ef4444)" }}>
            <p style={{ color: "var(--error, #ef4444)", margin: 0 }}>
              {error}. Make sure the backend is running on port 8080.
            </p>
          </div>
        )}

        {submitted && result && (
          <div className="result-box">
            <h3 style={{ marginTop: 0 }}>Your estimates</h3>
            <p>
              <strong>Maintenance (TDEE):</strong>{" "}
              <strong>{result.maintenanceKcal} kcal/day</strong>
            </p>
            <p>
              <strong>Suggested intake for selected goal:</strong>{" "}
              <strong>{result.suggestedKcal} kcal/day</strong>
            </p>
            <p style={{ fontSize: "0.9rem", color: "var(--text-muted)" }}>{result.note}</p>
          </div>
        )}

        <p className="disclaimer">
          BMR uses the Mifflin–St Jeor equation; TDEE multiplies BMR by activity factor. Individual
          variation is normal — treat as a starting point and adjust by tracking weight over weeks.
        </p>
      </div>
    </div>
  );
}
