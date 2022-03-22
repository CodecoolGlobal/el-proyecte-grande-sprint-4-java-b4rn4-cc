import React from "react";

const Login = () => {
  return (
    <>
      <form action="/login" method="post">
        <h2>Please Sign In</h2>
        <div>
          <label htmlFor="username">Username</label>
          <input type="text" name="username" id="username" placeholder="Username" required autoFocus />
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
