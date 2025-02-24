import React from 'react'

export default function Login(props) {

    const user ={
        username: "Saret",
        email: "sa@gmail.com"
    }
    const handleClick = () => {
        props.handleLogin(user);
    }
  return (
    <section>
        <h2>Login section</h2>
        <button onClick={handleClick}>Login</button>
    </section>
  )
}
