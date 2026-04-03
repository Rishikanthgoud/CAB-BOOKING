import { useState } from 'react'
import Home from './pages/Home'
import BookRide from './pages/BookRide'
import MyRides from './pages/MyRides'
import DriversDashboard from './pages/DriversDashboard'
import Login from './pages/Login'
import Register from './pages/Register'

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const [activePage, setActivePage] = useState('home')
  const [authMode, setAuthMode] = useState('register')
  const [pendingPhone, setPendingPhone] = useState('')
  const [rides, setRides] = useState([])

  if (isAuthenticated) {
    if (activePage === 'book-ride') {
      return (
        <BookRide
          onBack={() => setActivePage('home')}
          onRideBooked={(newRide) => {
            setRides((previousRides) => [newRide, ...previousRides])
            setActivePage('my-rides')
          }}
        />
      )
    }

    if (activePage === 'my-rides') {
      return <MyRides rides={rides} onBack={() => setActivePage('home')} />
    }

    if (activePage === 'drivers') {
      return <DriversDashboard rides={rides} onBack={() => setActivePage('home')} />
    }

    return <Home onNavigate={setActivePage} />
  }

  if (authMode === 'register') {
    return (
      <Register
        onSwitchToLogin={(phone = '') => {
          setPendingPhone(phone)
          setAuthMode('login')
        }}
      />
    )
  }

  return (
    <Login
      initialPhone={pendingPhone}
      onSwitchToRegister={() => setAuthMode('register')}
      onLoginSuccess={() => {
        setIsAuthenticated(true)
        setActivePage('home')
      }}
    />
  )
}

export default App
