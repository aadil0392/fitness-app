export default function Contact() {
  return (
    <div className="page">
      <div className="container container-narrow">
        <h1>Contact us</h1>
        <p className="lead">
          We’d love to hear your feedback, partnership ideas, or bug reports.
        </p>
        <div className="card">
          <p>
            <strong>Email</strong>
            <br />
            <a href="mailto:hello@fitcoach.example">hello@fitcoach.example</a>
          </p>
          <p className="disclaimer" style={{ marginTop: "1rem", border: "none", padding: 0 }}>
            Replace this address with your real support email when you deploy. For urgent health
            concerns, contact a licensed professional—not this site.
          </p>
        </div>
      </div>
    </div>
  );
}
