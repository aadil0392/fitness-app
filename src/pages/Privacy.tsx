import { Link } from "react-router-dom";

export default function Privacy() {
  return (
    <div className="page">
      <div className="container container-narrow">
        <h1>Privacy policy</h1>
        <p className="muted">Last updated: {new Date().toLocaleDateString()}</p>
        <h2>What we collect</h2>
        <p>
          This demo app runs mostly in your browser. When you use calculators or food search, your
          inputs are sent to our backend API to produce results. We do not require an account and do
          not intentionally store your questionnaire answers long-term unless you add that feature
          later.
        </p>
        <h2>Third parties</h2>
        <p>
          Food search queries are forwarded to Open Food Facts. Their privacy practices apply to
          that service. Exercise images may be loaded from external hosts (e.g. Wikimedia); those
          sites may log requests as normal web traffic.
        </p>
        <h2>Contact</h2>
        <p>
          Questions: see our <Link to="/contact">Contact</Link> page.
        </p>
      </div>
    </div>
  );
}
