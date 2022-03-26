import React, { useState } from "react";
import { useNavigate } from "react-router";

const Login = () => {
  const [username, setUsername] = useState("");
  const navigate = useNavigate();

  const handler = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    fetch("/login", {
      method: "POST",
      headers: { "content-type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams(formData),
    }).then((response) => {
      if (response.status === 200) {
        navigate("/" + username);
      }
      // message when wrong username password combo
    });
  };

  return (
    <>
      <form onSubmit={handler} action="/login" method="post">
        <h2>Please Sign In</h2>
        <div>
          <label htmlFor="username">Username</label>
          <input
            onChange={(e) => setUsername(e.target.value)}
            value={username}
            type="text"
            name="username"
            id="username"
            placeholder="Username"
            required
            autoFocus
          />
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input type="password" name="password" id="password" placeholder="Password" required />
        </div>
        <button type="submit">Sign in</button>
      </form>
    </>
  );
};

export default Login;
