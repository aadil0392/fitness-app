# FitCoach MVP

Science-style fitness MVP with a **Java (Spring Boot) backend** and **React frontend**:

1. **Maintenance calories** — Questionnaire → TDEE using Mifflin–St Jeor BMR + activity factors.
2. **Workout plan** — Splits with each exercise showing a **form guide** (GIF or diagram URL + short cue).
3. **Food calories** — Search typed food names; backend queries **Open Food Facts** for approximate kcal.
4. **Site footer** — About, Contact, Privacy, Terms.

## Run locally

**1. Backend (Java 17+)** — no global Maven install needed; the project includes the Maven Wrapper.

```bash
cd backend
chmod +x mvnw   # first time on Mac/Linux, if needed
./mvnw spring-boot:run
```

If you already have Maven installed, you can use `mvn spring-boot:run` instead.

API runs at http://localhost:8080. Main endpoints: `POST /api/calories`, `POST /api/workout`, `POST /api/food/search` (merged search). For faster UX the UI uses **`GET /api/food/indian?query=...`** (instant Indian staples) and **`POST /api/food/off`** (Open Food Facts; often slow).

**2. Frontend (React)**

```bash
npm install
npm run dev
```

Open http://localhost:5173. The app calls the backend at `http://localhost:8080` by default. Override with `VITE_API_URL` (see `.env.example`).

## Build for production

**Backend:** `cd backend && ./mvnw -DskipTests package` → run the JAR from `target/` (e.g. `java -jar target/backend-1.0.0.jar`).

**Frontend:** Set `VITE_API_URL` to your deployed API URL, then:

```bash
npm run build
```

Output: `dist/` — static files ready to host anywhere.

## Deploy publicly

## Recommended Free Setup (Frontend + Backend)

Use this combo for easiest free public access:

- Frontend: **Vercel** (free static hosting)
- Backend: **Render** Web Service (free tier, sleeps when idle)

### 1) Deploy backend on Render (free)

1. Push repo to GitHub.
2. In Render, create a **Web Service** from your repo.
3. Configure:
   - Root directory: `backend`
   - Environment: `Java`
   - Build command: `./mvnw -DskipTests package`
   - Start command: `java -jar target/backend-1.0.0.jar`
4. Add environment variable:
   - `fitcoach.cors.allowed-origins` = your frontend URL(s), comma separated.
     Example: `https://your-app.vercel.app`
5. Deploy and copy your backend URL (example: `https://fitcoach-backend.onrender.com`).

If your Render account does not show `Java` runtime, use Docker instead:

- Environment: `Docker`
- Root directory: `backend`
- Dockerfile path: `backend/Dockerfile`
- Build/start commands: leave default (Dockerfile handles both)

### 2) Deploy frontend on Vercel (free)

1. Import the same repo in Vercel.
2. Keep project root as repo root.
3. Framework preset: **Vite**.
4. Add environment variable:
   - `VITE_API_URL` = your Render backend URL
     Example: `https://fitcoach-backend.onrender.com`
5. Deploy.

### 3) Connect both sides

After frontend deploys, update backend CORS:

- In Render env vars, set `fitcoach.cors.allowed-origins` to your Vercel URL.
- Redeploy backend.

Now the site is public and usable by anyone via the Vercel URL.

Notes:
- Render free tier may cold-start after inactivity (first API call can be slow).
- If you want zero cold starts, you need a paid backend host.

### Vercel

1. Push this repo to GitHub.
2. Import the project in [Vercel](https://vercel.com) — framework preset **Vite**.
3. Build command: `npm run build` — output directory: `dist`.
4. Deploy — you get a public URL.

### Netlify

1. New site → connect repo.
2. Build: `npm run build`, publish: `dist`.
3. Optional: add `_redirects` in `public/` with `/* /index.html 200` for SPA routing (Netlify auto-detects Vite often).

### GitHub Pages

1. In `vite.config.ts`, set `base: '/your-repo-name/'`.
2. Build and deploy `dist` to `gh-pages` branch, or use GitHub Actions.

### Any static host

Upload the contents of `dist/` to S3, Cloudflare Pages, or your own server. Configure the server to serve `index.html` for client-side routes (SPA fallback).

## Disclaimer

Not medical advice. For individualized plans, see professionals and resources like [Built With Science](https://builtwithscience.com/).
