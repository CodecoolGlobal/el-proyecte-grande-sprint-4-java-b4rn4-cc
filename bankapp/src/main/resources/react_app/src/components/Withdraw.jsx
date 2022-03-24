import React, {useEffect, useState} from "react";
import {useLocation} from "react-router-dom";
import {apiPost} from "../FetchApis";
import {loadProp} from "./ReloadMemory";

const Withdraw = ({ accounts }) => {
  const preset = useLocation().state;
  const selected = loadProp(preset, 'selected', {accountNumber: null});   //load first/default account when empty

  const [amount, setAmount] = useState(0);
  let [sender, setSender] = useState(selected.accountNumber);
  let [currency, setCurrency] = useState("");
  let [message, setMessage] = useState("");


  useEffect(() => {
    if(accounts !== undefined && accounts.length !== 0) {
      updateForm(selected.accountNumber)
    }
  }, [accounts]);

  const submit = (e) => {
    e.preventDefault();
    apiPost("/account/ATM-withdraw", { amount, currency, sender:{accountNumber: sender}, message })
        .then(() => {
          setAmount(0);
          setMessage("");
        });
  };

  function updateForm(accNumber) {
    if (accNumber !== null) {
      for (let acc of accounts) {
        if (acc.accountNumber === accNumber) {
          setCurrency(acc.currency);
          localStorage.setItem('selected', JSON.stringify({accountNumber: accNumber}));
        }
      }
      setSender(accNumber);
    } else {
      setSender(accounts[0].accountNumber)
      setCurrency(accounts[0].currency);
    }
  }

  return (
    <div className="transfer-container">
      <h1>Withdraw</h1>
      <form className="transfer-form" onSubmit={submit}>
        <div>
          <label>Sender:</label>
          <select
              value={sender ? String(sender) : ""}
              name="accNumber"
              id="accNumber"
              onChange={(event) =>
              updateForm(event.target.value)
            }
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
}

export default Withdraw;
