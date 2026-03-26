import { Link } from "react-router-dom";

export default function Footer() {
  return (
    <footer className="site-footer">
      <div className="footer-inner">
        <div className="footer-brand">
          <strong>FitCoach</strong>
          <span className="footer-tagline">Train smarter. Fuel better.</span>
        </div>
        <nav className="footer-links" aria-label="Footer">
          <Link to="/about">About us</Link>
          <Link to="/contact">Contact</Link>
          <Link to="/privacy">Privacy</Link>
          <Link to="/terms">Terms</Link>
        </nav>
        <p className="footer-copy">
          © {new Date().getFullYear()} FitCoach. Not medical advice.
        </p>
      </div>
    </footer>
  );
}
