export default function About() {
  return (
    <div className="page">
      <div className="container container-narrow">
        <h1>About FitCoach</h1>
        <p className="lead">
          FitCoach is a fitness companion that helps you estimate maintenance calories, explore
          structured workout plans with form references, and look up approximate calories in common
          foods.
        </p>
        <h2>Our approach</h2>
        <p>
          We combine established formulas (like Mifflin–St Jeor for resting metabolism) with
          practical training splits and crowd-sourced nutrition data. The goal is clarity and
          consistency—not a substitute for a coach, dietitian, or physician.
        </p>
        <h2>Data sources</h2>
        <p>
          Food nutrition is powered by{" "}
          <a href="https://world.openfoodfacts.org/" target="_blank" rel="noopener noreferrer">
            Open Food Facts
          </a>
          . Exercise illustrations link to educational media (including Wikimedia Commons) selected
          to reinforce safe, standard movement patterns.
        </p>
      </div>
    </div>
  );
}
