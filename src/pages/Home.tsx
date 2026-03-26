import { Link } from "react-router-dom";

const HERO_IMAGE =
  "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=1920&q=80";
const FEATURES_IMAGE =
  "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=1200&q=80";

export default function Home() {
  return (
    <div className="home-page">
      <section
        className="hero hero-with-bg"
        style={{
          backgroundImage: `linear-gradient(to bottom, rgba(10, 12, 16, 0.75) 0%, rgba(10, 12, 16, 0.9) 100%), url(${HERO_IMAGE})`,
        }}
      >
        <div className="hero-inner">
          <h1>Your smart fitness coach</h1>
          <p>
            Estimate maintenance calories, build a workout plan with form visuals, and search food
            calories from a global database—all in one place.
          </p>
          <div className="hero-cta">
            <Link to="/calories" className="btn">
              Calculate maintenance calories
            </Link>
            <Link to="/workout" className="btn btn-ghost">
              Get workout plan
            </Link>
            <Link to="/food" className="btn btn-ghost">
              Look up food calories
            </Link>
          </div>
        </div>
      </section>

      <section
        className="home-features"
        style={{
          backgroundImage: `linear-gradient(to right, rgba(10, 12, 16, 0.92) 0%, rgba(10, 12, 16, 0.7) 50%, transparent 100%), url(${FEATURES_IMAGE})`,
        }}
      >
        <div className="container">
          <h2>What you get</h2>
          <div className="features-grid features-grid-3">
            <div className="feature-card">
              <span className="feature-icon" aria-hidden>🔥</span>
              <h3>Maintenance calories</h3>
              <p>
                Uses the Mifflin–St Jeor equation and your activity level to estimate TDEE—your
                daily maintenance intake. Optional goal (lose / maintain / gain) gives a suggested
                calorie target.
              </p>
              <Link to="/calories" className="feature-link">
                Calculate calories →
              </Link>
            </div>
            <div className="feature-card">
              <span className="feature-icon" aria-hidden>💪</span>
              <h3>Workout plans</h3>
              <p>
                Full-body, upper/lower, or push/pull/legs—with GIFs and diagrams for each exercise
                so you can check posture, not just the name.
              </p>
              <Link to="/workout" className="feature-link">
                Get workout plan →
              </Link>
            </div>
            <div className="feature-card">
              <span className="feature-icon" aria-hidden>🥗</span>
              <h3>Food calories</h3>
              <p>
                Type any food or product name and see approximate kcal from Open Food Facts—great
                for meal planning next to your TDEE target.
              </p>
              <Link to="/food" className="feature-link">
                Search foods →
              </Link>
            </div>
          </div>
          <p className="home-disclaimer">
            Not medical advice. Consult a professional before changing diet or exercise. Inspired
            by science-based coaching such as{" "}
            <a href="https://builtwithscience.com/" target="_blank" rel="noopener noreferrer">
              Built With Science
            </a>
            .
          </p>
        </div>
      </section>
    </div>
  );
}
