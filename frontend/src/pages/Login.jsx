import { useState } from 'react'
import './Login.css'

const SEND_OTP_ENDPOINT = 'http://localhost:8080/auth/send-otp'
const VERIFY_OTP_ENDPOINT = 'http://localhost:8080/auth/verify-otp'

function Login({ onLoginSuccess, onSwitchToRegister, initialPhone = '' }) {
  const [phone, setPhone] = useState(initialPhone)
  const [otp, setOtp] = useState('')
  const [otpSent, setOtpSent] = useState(false)
  const [isSending, setIsSending] = useState(false)
  const [isVerifying, setIsVerifying] = useState(false)
  const [statusMessage, setStatusMessage] = useState('')

  const sendOtp = async () => {
    const phoneDigits = phone.trim().replace(/\D/g, '')

    if (phoneDigits.length !== 10) {
      setStatusMessage('Please enter a valid 10-digit phone number.')
      return
    }

    setIsSending(true)
    setStatusMessage('Sending OTP...')

    try {
      const response = await fetch(`${SEND_OTP_ENDPOINT}?mobile=${encodeURIComponent(phoneDigits)}`, {
        method: 'POST',
      })

      if (!response.ok) {
        const errorText = await response.text()
        throw new Error(errorText || 'Failed to send OTP.')
      }

      setStatusMessage('OTP sent to your phone number.')
      setOtpSent(true)
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Unable to connect to server.'
      setStatusMessage(errorMessage)
    } finally {
      setIsSending(false)
    }
  }

  const verifyOtp = async () => {
    const phoneDigits = phone.trim().replace(/\D/g, '')
    const otpValue = otp.trim()

    if (phoneDigits.length !== 10) {
      setStatusMessage('Please enter a valid 10-digit phone number.')
      return
    }

    if (!/^\d{6}$/.test(otpValue)) {
      setStatusMessage('Please enter a valid 6-digit OTP.')
      return
    }

    setIsVerifying(true)
    setStatusMessage('Verifying OTP...')

    try {
      const response = await fetch(
        `${VERIFY_OTP_ENDPOINT}?mobile=${encodeURIComponent(phoneDigits)}&otp=${encodeURIComponent(otpValue)}`,
        {
          method: 'POST',
        },
      )

      const resultText = await response.text()

      if (!response.ok) {
        throw new Error(resultText || 'OTP verification failed.')
      }

      setStatusMessage(resultText || 'OTP verified successfully.')

      if (resultText.trim().toLowerCase() === 'login successful') {
        setTimeout(() => {
          onLoginSuccess?.()
        }, 500)
      }
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Unable to connect to server.'
      setStatusMessage(errorMessage)
    } finally {
      setIsVerifying(false)
    }
  }

  const handleSubmit = async (event) => {
    event.preventDefault()

    if (otpSent) {
      await verifyOtp()
      return
    }

    await sendOtp()
  }

  return (
    <main className="login-page">
      <section className="login-card">
        <h1 className="login-title">Login with OTP</h1>
        <p className="login-subtitle">Use your registered phone number to login</p>

        <form className="login-form" onSubmit={handleSubmit}>
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
            disabled={otpSent}
          />

          {otpSent && (
            <>
              <label htmlFor="otp">OTP</label>
              <input
                id="otp"
                name="otp"
                type="text"
                value={otp}
                onChange={(event) => setOtp(event.target.value.replace(/\D/g, '').slice(0, 6))}
                placeholder="Enter 6-digit OTP"
                inputMode="numeric"
                pattern="[0-9]{6}"
                autoComplete="one-time-code"
                required
              />
            </>
          )}

          {!otpSent ? (
            <button type="submit" className="neon-glass-button" disabled={isSending}>
              {isSending ? 'Sending...' : 'Get OTP'}
            </button>
          ) : (
            <>
              <button type="submit" className="neon-glass-button" disabled={isVerifying}>
                {isVerifying ? 'Verifying...' : 'Verify OTP'}
              </button>
              <button
                type="button"
                className="secondary-button neon-glass-button"
                onClick={sendOtp}
                disabled={isSending || isVerifying}
              >
                {isSending ? 'Sending...' : 'Resend OTP'}
              </button>
              <button
                type="button"
                className="secondary-button neon-glass-button"
                onClick={() => {
                  setOtpSent(false)
                  setOtp('')
                  setStatusMessage('')
                }}
                disabled={isSending || isVerifying}
              >
                Change Number
              </button>
            </>
          )}

          {statusMessage && <p className="login-status">{statusMessage}</p>}
        </form>

        <p className="auth-switch-text">
          Don&apos;t have account?{' '}
          <button type="button" className="auth-link-button" onClick={() => onSwitchToRegister?.()}>
            Register
          </button>
        </p>
      </section>
    </main>
  )
}

export default Login
