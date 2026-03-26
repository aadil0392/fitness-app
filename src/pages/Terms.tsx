export default function Terms() {
  return (
    <div className="page">
      <div className="container container-narrow">
        <h1>Terms of use</h1>
        <p className="muted">Last updated: {new Date().toLocaleDateString()}</p>
        <p>
          By using FitCoach you agree that this service is provided <strong>as-is</strong>, for
          general information and education only. Nothing on this site is medical, nutritional, or
          training advice tailored to you.
        </p>
        <h2>Assumption of risk</h2>
        <p>
          Physical activity and diet changes carry risk. Consult your physician before starting a
          program, especially if you have health conditions, injuries, or are pregnant.
        </p>
        <h2>Accuracy</h2>
        <p>
          Calorie estimates, workout suggestions, and food database entries may be incomplete or
          inaccurate. Always verify packaged food labels and adjust plans based on your own
          progress and professional guidance.
        </p>
        <h2>Liability</h2>
        <p>
          To the fullest extent permitted by law, FitCoach and its operators disclaim liability for
          any damages arising from use of this site.
        </p>
      </div>
    </div>
  );
}
