import './DriversDashboard.css'

function DriversDashboard({ rides = [], onBack }) {
  const assignedDrivers = rides.map((ride) => ({
    rideId: ride.id,
    pickupLocation: ride.pickupLocation,
    dropLocation: ride.dropLocation,
    vehicleType: ride.vehicleType,
    ...ride.driver,
  }))

  return (
    <main className="drivers-page">
      <section className="drivers-card">
        <div className="drivers-title">Driver Dashboard</div>
        <div className="divider"></div>

        {assignedDrivers.length === 0 ? (
          <p className="drivers-empty">No driver assigned yet. Book a ride to view driver details.</p>
        ) : (
          <div className="drivers-list">
            {assignedDrivers.map((driver) => (
              <article key={driver.rideId} className="driver-item">
                <div className="driver-row">
                  <span className="label">Driver Name</span>
                  <span className="value">{driver.name}</span>
                </div>
                <div className="driver-row">
                  <span className="label">Phone</span>
                  <span className="value">{driver.phone}</span>
                </div>
                <div className="driver-row">
                  <span className="label">Vehicle</span>
                  <span className="value">{driver.vehicle}</span>
                </div>
                <div className="driver-row">
                  <span className="label">Vehicle Type</span>
                  <span className="value">{driver.vehicleType}</span>
                </div>
                <div className="driver-row">
                  <span className="label">Assigned Route</span>
                  <span className="value">{driver.pickupLocation} to {driver.dropLocation}</span>
                </div>
              </article>
            ))}
          </div>
        )}

        <button type="button" className="back-btn neon-glass-button" onClick={onBack}>
          Back to Home
        </button>
      </section>
    </main>
  )
}

export default DriversDashboard
