import { useState } from 'react'
import { ArrowLeft, MapPin, Navigation } from 'lucide-react'
import './BookRide.css'

const BOOK_RIDE_BASE_URL = 'http://localhost:8080/api/rides/book'
const DEFAULT_USER_ID = 1

const DRIVERS_BY_VEHICLE = {
  sedan: [
    { name: 'Ravi Kumar', phone: '9876543210', vehicle: 'Maruti Dzire' },
    { name: 'Amit Verma', phone: '9860011223', vehicle: 'Hyundai Aura' },
  ],
  suv: [
    { name: 'Suresh Nair', phone: '9845012345', vehicle: 'Mahindra XUV700' },
    { name: 'Deepak Singh', phone: '9822445566', vehicle: 'Toyota Innova' },
  ],
  mini: [
    { name: 'Manoj Patil', phone: '9812345678', vehicle: 'Maruti Swift' },
    { name: 'Karthik R', phone: '9800099000', vehicle: 'Hyundai i20' },
  ],
}

function BookRide({ onBack, onRideBooked }) {
  const [pickupLocation, setPickupLocation] = useState('')
  const [dropLocation, setDropLocation] = useState('')
  const [passengers, setPassengers] = useState('1')
  const [vehicleType, setVehicleType] = useState('sedan')
  const [statusMessage, setStatusMessage] = useState('')
  const [isBooking, setIsBooking] = useState(false)

  const handleSubmit = async (event) => {
    event.preventDefault()

    const pickupValue = pickupLocation.trim()
    const dropValue = dropLocation.trim()
    const passengerCount = Number(passengers)

    if (!pickupValue || !dropValue) {
      setStatusMessage('Please enter pickup and drop locations.')
      return
    }

    if (!Number.isInteger(passengerCount) || passengerCount < 1 || passengerCount > 8) {
      setStatusMessage('Passengers should be between 1 and 8.')
      return
    }

    setIsBooking(true)
    setStatusMessage('Booking ride...')

    try {
      const response = await fetch(`${BOOK_RIDE_BASE_URL}/${DEFAULT_USER_ID}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          pickupLocation: pickupValue,
          dropLocation: dropValue,
          passengers: passengerCount,
          vehicleType,
        }),
      })

      const resultText = await response.text()

      if (!response.ok) {
        throw new Error(resultText || 'Unable to book ride.')
      }

      const eligibleDrivers = DRIVERS_BY_VEHICLE[vehicleType] || DRIVERS_BY_VEHICLE.sedan
      const assignedDriver = eligibleDrivers[Math.floor(Math.random() * eligibleDrivers.length)]

      const bookedRide = {
        id: Date.now(),
        pickupLocation: pickupValue,
        dropLocation: dropValue,
        passengers: passengerCount,
        vehicleType,
        status: 'BOOKED',
        driver: {
          ...assignedDriver,
          vehicleType,
        },
      }

      setStatusMessage(resultText || 'Ride booked successfully.')
      setPickupLocation('')
      setDropLocation('')
      setPassengers('1')
      setVehicleType('sedan')

      setTimeout(() => {
        onRideBooked?.(bookedRide)
      }, 350)
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Unable to connect to server.'
      setStatusMessage(errorMessage)
    } finally {
      setIsBooking(false)
    }
  }

  return (
    <main className="book-ride-page">
      <section className="book-ride-card">
        <div className="book-ride-header">
          <button type="button" className="back-button neon-glass-button" onClick={onBack}>
            <ArrowLeft size={16} />
            Back to Home
          </button>
          <h1>Book a Ride</h1>
          <p>Create a new ride request for your cab booking system.</p>
        </div>

        <form className="book-ride-form" onSubmit={handleSubmit}>
          <label htmlFor="pickupLocation">
            <MapPin size={16} />
            Pickup Location
          </label>
          <input
            id="pickupLocation"
            type="text"
            value={pickupLocation}
            onChange={(event) => setPickupLocation(event.target.value)}
            placeholder="Enter pickup location"
            required
          />

          <label htmlFor="dropLocation">
            <Navigation size={16} />
            Drop Location
          </label>
          <input
            id="dropLocation"
            type="text"
            value={dropLocation}
            onChange={(event) => setDropLocation(event.target.value)}
            placeholder="Enter drop location"
            required
          />

          <label htmlFor="passengers">No. of Passengers</label>
          <input
            id="passengers"
            type="number"
            min="1"
            max="8"
            value={passengers}
            onChange={(event) => setPassengers(event.target.value)}
            required
          />

          <label htmlFor="vehicleType">Vehicle Type</label>
          <select
            id="vehicleType"
            value={vehicleType}
            onChange={(event) => setVehicleType(event.target.value)}
          >
            <option value="sedan">Sedan</option>
            <option value="mini">Mini</option>
            <option value="suv">SUV</option>
          </select>

          <button type="submit" className="book-submit-button neon-glass-button" disabled={isBooking}>
            {isBooking ? 'Booking...' : 'Confirm Ride'}
          </button>

          {statusMessage && <p className="book-status-message">{statusMessage}</p>}
        </form>
      </section>
    </main>
  )
}

export default BookRide
