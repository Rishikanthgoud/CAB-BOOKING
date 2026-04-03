import { useState } from 'react'
import './Register.css'

const REGISTER_ENDPOINT = 'http://localhost:8080/api/users/newuser'

function Register({ onSwitchToLogin }) {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [phone, setPhone] = useState('')
  const [isRegistering, setIsRegistering] = useState(false)
  const [statusMessage, setStatusMessage] = useState('')

  const handleSubmit = async (event) => {
    event.preventDefault()

    const nameValue = name.trim()
    const emailValue = email.trim()
    const passwordValue = password.trim()
    const phoneDigits = phone.trim().replace(/\D/g, '')

    if (!nameValue) {
      setStatusMessage('Please enter your name.')
      return
    }

    if (!/^\S+@\S+\.\S+$/.test(emailValue)) {
      setStatusMessage('Please enter a valid email address.')
      return
    }

    if (passwordValue.length < 4) {
      setStatusMessage('Password should be at least 4 characters long.')
      return
    }

    if (phoneDigits.length !== 10) {
      setStatusMessage('Please enter a valid 10-digit phone number.')
      return
    }

    setIsRegistering(true)
    setStatusMessage('Creating your account...')

    try {
      const response = await fetch(REGISTER_ENDPOINT, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: nameValue,
          email: emailValue,
          password: passwordValue,
          phoneNo: phoneDigits,
        }),
      })

      const resultText = await response.text()

      if (!response.ok) {
        throw new Error(resultText || 'Registration failed.')
      }

      setStatusMessage('Registration successful. Please login with OTP.')

      setTimeout(() => {
        onSwitchToLogin?.(phoneDigits)
      }, 500)
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Unable to connect to server.'
      setStatusMessage(errorMessage)
    } finally {
      setIsRegistering(false)
    }
  }

  return (
    <main className="register-page">
      <section className="register-card">
        <h1 className="register-title">Create Account</h1>
        <p className="register-subtitle">Register first, then login using phone OTP.</p>

        <form className="register-form" onSubmit={handleSubmit}>
          <label htmlFor="name">Name</label>
          <input
            id="name"
            name="name"
            type="text"
            value={name}
            onChange={(event) => setName(event.target.value)}
            placeholder="Enter your name"
            autoComplete="name"
            required
          />

          <label htmlFor="email">Email</label>
          <input
            id="email"
            name="email"
            type="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            placeholder="Enter your email"
            autoComplete="email"
            required
          />

          <label htmlFor="password">Password</label>
          <input
            id="password"
            name="password"
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            placeholder="Enter your password"
            autoComplete="new-password"
            required
          />

          <label htmlFor="phone">Phone Number</label>
          <input
            id="phone"
            name="phone"
            type="tel"
            value={phone}
            onChange={(event) => setPhone(event.target.value)}
            placeholder="Enter 10-digit phone number"
            inputMode="numeric"
            pattern="[0-9]{10}"
            autoComplete="tel"
            required
          />

          <button type="submit" className="neon-glass-button" disabled={isRegistering}>
            {isRegistering ? 'Registering...' : 'Register'}
          </button>

          {statusMessage && <p className="register-status">{statusMessage}</p>}
        </form>

        <p className="auth-switch-text">
          Already registered?{' '}
          <button type="button" className="auth-link-button" onClick={() => onSwitchToLogin?.(phone.trim())}>
            Login
          </button>
        </p>
      </section>
    </main>
  )
}

export default Register
