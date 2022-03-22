import React, {useEffect, useState} from "react";
import { useLocation } from 'react-router-dom';
import {apiPost} from "../FetchApis";
import {loadProp} from "./ReloadMemory";

const Deposit = ({ accounts }) => {
  const preset = useLocation().state;
  let loadedAccounts = loadProp(accounts, 'accounts', [{accountNumber: "", currency: "EUR"}]);
  const form = loadProp(preset, 'form', loadedAccounts[0]);   //load first/default account when empty

  const [amount, setAmount] = useState(0);
  const [recipient, setRecipient] = useState(loadProp(preset,'accNum', "").accountNumber);
  const [message, setMessage] = useState("");
  let [currency, setCurrency] = useState(form.currency);


  useEffect(() => {
    localStorage.setItem('accounts', JSON.stringify(loadedAccounts));
    localStorage.setItem('form', JSON.stringify({currency: currency, accountNumber: recipient}));
  }, []);

  // store info when selecting (will run a lot when typing)
  useEffect(() => {
    localStorage.setItem('accNum', JSON.stringify({accountNumber: recipient}));
  }, [recipient]);

  const submit = (e) => {
    e.preventDefault();
    apiPost("/account/ATM-deposit",{ amount, currency, recipient:{accountNumber: recipient}, message })
        .then(() => {
          setAmount(0);
          setMessage("");
        });
  }

  function getCurrency(accNumber) {
    for (let acc of loadedAccounts) {
      if (acc.accountNumber === accNumber) {
        setCurrency(acc.currency);
      }
    }
  }

  return <div className="transfer-container">
    <h1>Deposit</h1>
    <form className="transfer-form" onSubmit={submit}>
      <div>
        <label htmlFor="recipientAccNumber">Recipient:</label>
        <select
            defaultValue={recipient ? String(recipient) : ""}
            name="accNumber"
            id="accNumber"
            onChange={(event) => {
              setRecipient(event.target.value);
              getCurrency(event.target.value);
            }}
        >
          {loadedAccounts.map((acc) => (
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
