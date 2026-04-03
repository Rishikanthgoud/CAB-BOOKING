import React from 'react'
import './MyRides.css'

const MyRides = ({ onBack }) => {
  return (
    <div className="my-rides-container">
      <div className="ride-card">
        <div className="ride-title">My Ride</div>

        <div className="divider"></div>

        <div className="ride-info">
          <div className="ride-row">
            <span className="label">From</span>
            <span className="value">Hyderabad</span>
          </div>

          <div className="ride-row">
            <span className="label">To</span>
            <span className="value">Visakhapatnam</span>
          </div>

          <div className="ride-row">
            <span className="label">Passengers</span>
            <span className="value">1</span>
          </div>

          <div className="ride-row">
            <span className="label">Vehicle</span>
            <span className="value">Mini</span>
          </div>

          <div className="ride-row">
            <span className="label">Status</span>
            <span className="status booked">BOOKED</span>
          </div>

          <div className="ride-row">
            <span className="label">Driver</span>
            <span className="value">Karthik R (9800099000)</span>
          </div>
        </div>

        <button type="button" className="back-btn neon-glass-button" onClick={onBack}>
          Back to Home
        </button>
      </div>
    </div>
  )
}

export default MyRides
