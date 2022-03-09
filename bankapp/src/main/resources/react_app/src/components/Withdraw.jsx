import React, { useState } from "react";
import {useLocation} from "react-router-dom";
import {apiPost} from "../FetchApis";

const Withdraw = ({ accounts }) => {
  const data = useLocation().state;
  const [amount, setAmount] = useState(0);
  const [sender, setSender] = useState(data.accNum);
  const [currency, setCurrency] = useState(data.currency);
  const [message, setMessage] = useState("");

  const submit = (e) => {
    e.preventDefault();
    apiPost("/account/ATM-withdraw", { amount, currency, sender, message });
    setAmount(0);
    setMessage("");
  };

  function getCurrency(accNumber) {
    for (let acc of accounts) {
      if (acc.accountNumber === accNumber) {
        setCurrency(acc.currency);
      }
    }
  }

  return (
    <div className="transfer-container">
      <h1>Withdraw</h1>
      <form className="transfer-form" onSubmit={submit}>
        <div>
          <label>Sender:</label>
          <select
              defaultValue={sender ? String(sender) : ""}
              name="accNumber"
              id="accNumber"
              onChange={(event) => {
              setSender(event.target.value);
              getCurrency(event.target.value);
            }}
          >
            {accounts.map((acc) => (
              <option key={acc.accountNumber} value={acc.accountNumber}>
                {acc.accountNumber}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="amount">Amount:</label>
          <input
            type="text"
            id="amount"
            name="amount"
            placeholder="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
        </div>
        <div
          style={{
            display: "flex",
            justifyContent: "spaceBetween",
            padding: "0 0 25px",
          }}
        >
          <p>Currency:</p>
          <p>
            <strong>{currency}</strong>
          </p>
        </div>
        <div>
          <label htmlFor="message">Message:</label>
          <input
            type="text"
            id="message"
            name="message"
            placeholder="Message"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
          />
        </div>
        <button type="submit">Transfer</button>
      </form>
    </div>
  );
};

export default Withdraw;
