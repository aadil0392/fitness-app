import { Outlet, Link } from "react-router-dom";
import Footer from "./Footer";

export default function Layout() {
  return (
    <div className="app-shell">
      <header className="nav">
        <Link to="/" className="nav-brand">
          FitCoach
        </Link>
        <nav className="nav-links" aria-label="Main">
          <Link to="/calories">TDEE</Link>
          <Link to="/workout">Workouts</Link>
          <Link to="/food">Food calories</Link>
        </nav>
      </header>
      <main className="app-main">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
}
