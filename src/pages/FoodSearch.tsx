import { useState } from "react";
import { getIndianFoods, postOpenFoodFactsSearch } from "../api/client";
import type { FoodItemBody } from "../api/client";

const ATTRIBUTION =
  "Indian items: approximate guides for common dishes. Packaged foods: Open Food Facts. Always verify with labels.";

function normalizeName(name: string | null | undefined): string {
  return (name || "").toLowerCase().replace(/\s+/g, " ").trim();
}

function mergeDedupe(primary: FoodItemBody[], extra: FoodItemBody[]): FoodItemBody[] {
  const seen = new Set<string>();
  const out: FoodItemBody[] = [];
  for (const item of primary) {
    const k = normalizeName(item.name);
    if (k && !seen.has(k)) {
      seen.add(k);
      out.push(item);
    }
  }
  for (const item of extra) {
    const k = normalizeName(item.name);
    if (k && !seen.has(k)) {
      seen.add(k);
      out.push(item);
    }
  }
  return out;
}

export default function FoodSearch() {
  const [query, setQuery] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [offPending, setOffPending] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [results, setResults] = useState<FoodItemBody[]>([]);
  const [searched, setSearched] = useState(false);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    const q = query.trim();
    if (!q) return;
    setError(null);
    setResults([]);
    setSearched(true);
    setSubmitting(true);
    setOffPending(true);

    try {
      const indian = await getIndianFoods(q);
      setResults(indian);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Indian lookup failed");
    } finally {
      setSubmitting(false);
    }

    postOpenFoodFactsSearch(q)
      .then((off) => {
        setResults((prev) => mergeDedupe(prev, off));
      })
      .catch(() => {
        /* OFF often slow or blocked — Indian results already visible */
      })
      .finally(() => {
        setOffPending(false);
      });
  }

  return (
    <div className="page">
      <div className="container container-wide">
        <h1 style={{ marginBottom: "0.5rem" }}>Food calorie lookup</h1>
        <p style={{ color: "var(--text-muted)", marginBottom: "1.5rem" }}>
          <strong>Indian dishes</strong> appear first (instant local guide). <strong>Open Food Facts</strong>{" "}
          loads packaged foods in the background (often 10–30 seconds).
        </p>

        <form className="card food-search-form" onSubmit={handleSubmit}>
          <label className="label" htmlFor="food-q">
            Food name
          </label>
          <div className="food-search-row">
            <input
              id="food-q"
              className="input food-search-input"
              type="search"
              placeholder="e.g. dosa, dal, banana, protein bar…"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              autoComplete="off"
            />
            <button type="submit" className="btn" disabled={submitting || !query.trim()}>
              {submitting ? "Loading…" : "Search"}
            </button>
          </div>
        </form>

        {offPending && (
          <p className="food-off-pending" style={{ fontSize: "0.9rem", color: "var(--accent)" }}>
            Searching global product database… results will appear below when ready.
          </p>
        )}

        {error && (
          <div className="card" style={{ marginTop: "1rem", borderColor: "var(--error, #ef4444)" }}>
            <p style={{ color: "var(--error, #ef4444)", margin: 0 }}>
              {error}. Is the backend running on port 8080?
            </p>
          </div>
        )}

        <p className="food-attribution" style={{ fontSize: "0.85rem", color: "var(--text-muted)", marginTop: "1rem" }}>
          {ATTRIBUTION}
        </p>

        {results.length > 0 && (
          <ul className="food-results">
            {results.map((item, i) => (
              <li key={`${normalizeName(item.name)}-${i}`} className="food-card card">
                {item.imageUrl && (
                  <div className="food-card-image">
                    <img src={item.imageUrl} alt="" loading="lazy" referrerPolicy="no-referrer" />
                  </div>
                )}
                <div className="food-card-body">
                  <h3 className="food-card-title">{item.name}</h3>
                  {item.brand && <p className="food-card-brand">{item.brand}</p>}
                  <p className="food-card-kcal">
                    <strong>{item.caloriesPer100g != null ? `${item.caloriesPer100g} kcal` : "—"}</strong>
                    <span className="food-kcal-label">
                      {item.quantityHint?.toLowerCase().includes("serving")
                        ? " (per serving where noted)"
                        : " (per 100 g / 100 ml typical)"}
                    </span>
                  </p>
                  {item.quantityHint && (
                    <p className="food-card-hint">{item.quantityHint}</p>
                  )}
                  {item.source && (
                    <p className="food-card-source">Source: {item.source}</p>
                  )}
                </div>
              </li>
            ))}
          </ul>
        )}

        {searched && !submitting && !offPending && results.length === 0 && !error && (
          <p style={{ color: "var(--text-muted)" }}>
            No matches. Try Indian names (idli, chapati) or a packaged brand + product for Open Food
            Facts.
          </p>
        )}

        <p className="disclaimer">
          Values depend on brand, recipe, and region. Always read the label on your actual package.
        </p>
      </div>
    </div>
  );
}
