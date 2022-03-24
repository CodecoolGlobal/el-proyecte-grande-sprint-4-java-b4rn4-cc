import React, {useEffect, useState} from "react";
import { useLocation } from 'react-router-dom';
import {apiPost} from "../FetchApis";
import {loadProp} from "./ReloadMemory";

const Deposit = ({ accounts }) => {
  const preset = useLocation().state;
  let selected = loadProp(preset, 'selected', {accountNumber: null});

  const [amount, setAmount] = useState(0);
  const [recipient, setRecipient] = useState(selected.accountNumber);
  let [currency, setCurrency] = useState("");
  const [message, setMessage] = useState("");

  // load currency when accounts are fetched
  useEffect(() => {
    if(accounts !== undefined && accounts.length !== 0) {
      updateForm(selected.accountNumber)
    }
  }, [accounts]);

  const submit = (e) => {
    e.preventDefault();
    apiPost("/account/ATM-deposit",{ amount, currency, recipient:{accountNumber: recipient}, message })
        .then(() => {
          setAmount(0);
          setMessage("");
        });
  }

  function updateForm(accNumber) {
    if(accNumber !== null) {
      for (let acc of accounts) {
        if (acc.accountNumber === accNumber) {
          setCurrency(acc.currency);
          localStorage.setItem('selected', JSON.stringify({accountNumber: accNumber}));
        }
      }
      setRecipient(accNumber);
    } else {
      setRecipient(accounts[0].accountNumber)
      setCurrency(accounts[0].currency);
    }

  }

  return <div className="transfer-container">
    <h1>Deposit</h1>
    <form className="transfer-form" onSubmit={submit}>
      <div>
        <label htmlFor="recipientAccNumber">Recipient:</label>
        <select
            // defaultValue={recipient ? String(recipient) : ""}
            value = {recipient ? String(recipient) : ""}
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
      <div style={{display: "flex", justifyContent: "spaceBetween", padding: "0 0 25px"}}>
        <p>Currency:</p>
        <p><strong>{currency}</strong></p>
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
  </div>;
};

export default Deposit;
