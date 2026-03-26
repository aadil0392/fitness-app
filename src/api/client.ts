const API_BASE = import.meta.env.VITE_API_URL || "http://localhost:8080";

async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  const url = `${API_BASE}${path}`;
  const res = await fetch(url, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || `HTTP ${res.status}`);
  }
  return res.json();
}

export interface CaloriesRequestBody {
  sex: string;
  ageYears: number;
  weightKg: number;
  heightCm: number;
  activity: string;
  goal?: string;
}

export interface CaloriesResponseBody {
  maintenanceKcal: number;
  suggestedKcal: number;
  note: string;
}

export interface ExerciseBody {
  name: string;
  gifUrl: string;
  formCue: string;
}

export interface WorkoutDayBody {
  name: string;
  focus: string;
  exercises: ExerciseBody[];
}

export interface WorkoutPlanResponseBody {
  title: string;
  summary: string;
  split: string;
  days: WorkoutDayBody[];
  tips: string[];
}

export interface WorkoutRequestBody {
  goal: string;
  daysPerWeek: number;
  equipment: string;
  experience?: string;
}

export interface FoodItemBody {
  name: string;
  brand?: string | null;
  caloriesPer100g?: number | null;
  quantityHint?: string | null;
  imageUrl?: string | null;
  source?: string | null;
}

export interface FoodSearchResponseBody {
  results: FoodItemBody[];
  attribution: string;
}

export function postCalories(body: CaloriesRequestBody): Promise<CaloriesResponseBody> {
  return request<CaloriesResponseBody>("/api/calories", {
    method: "POST",
    body: JSON.stringify(body),
  });
}

export function postWorkoutPlan(body: WorkoutRequestBody): Promise<WorkoutPlanResponseBody> {
  return request<WorkoutPlanResponseBody>("/api/workout", {
    method: "POST",
    body: JSON.stringify(body),
  });
}

export function postFoodSearch(query: string): Promise<FoodSearchResponseBody> {
  return request<FoodSearchResponseBody>("/api/food/search", {
    method: "POST",
    body: JSON.stringify({ query }),
  });
}

/** Fast local Indian staples (milliseconds). */
export function getIndianFoods(query: string): Promise<FoodItemBody[]> {
  const q = encodeURIComponent(query.trim());
  return request<FoodItemBody[]>(`/api/food/indian?query=${q}`);
}

/** Open Food Facts — can take 10–30s; use after showing Indian results. */
export function postOpenFoodFactsSearch(query: string): Promise<FoodItemBody[]> {
  return request<FoodItemBody[]>("/api/food/off", {
    method: "POST",
    body: JSON.stringify({ query }),
  });
}
