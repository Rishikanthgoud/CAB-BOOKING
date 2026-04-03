import React from "react";
import "./Home.css";
import { CarTaxiFront, Car, Users, Truck } from "lucide-react";

function Home({ onNavigate }) {
  return (
    <div className="home-container">
      <div className="welcome-card">

        <div className="logo">
          <CarTaxiFront size={40} />
        </div>

        <h1>Welcome</h1>
        <p className="subtitle">
          Login successful. You are now on the home page.
        </p>

        <div className="home-buttons">

          <button className="btn neon-glass-button" onClick={() => onNavigate?.("book-ride")}>
            <Car size={18} />
            Book Ride
          </button>

          <button className="btn neon-glass-button" onClick={() => onNavigate?.("my-rides")}>
            <Car size={18} />
            View Rides
          </button>

          <button className="btn neon-glass-button" onClick={() => onNavigate?.("drivers")}>
            <Users size={18} />
            Drivers
          </button>

          <button className="btn neon-glass-button">
            <Truck size={18} />
            Vehicles
          </button>

        </div>
      </div>
    </div>
  );
}

export default Home;