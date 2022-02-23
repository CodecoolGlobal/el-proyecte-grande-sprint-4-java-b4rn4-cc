import React, { useState } from "react";

const Transfermoney = ({ transferMoney, accounts }) => {
  const [amount, setAmount] = useState(0);
  const [sender, setSender] = useState("");
  const [recipient, setRecipient] = useState("");
  const [message, setMessage] = useState("");

  const submit = (e) => {
    e.preventDefault();
    transferMoney({ amount, sender, recipient, message });

    setAmount(0);
    setMessage("");
    setRecipient("");
    setSender("");
  };

  return (
    <div className="transfer-container">
      <h1>Transfer Money</h1>
      <select
        name="accNumber"
        id="accNumber"
        onChange={(event) => setSender(event.target.value)}
      >
        {accounts.map((acc) => (
          <option key={acc.accountNumber} value={acc.accountNumber}>
            {acc.accountNumber}
          </option>
        ))}
      </select>
      <form className="transfer-form" onSubmit={submit}>
        <div>
          <label htmlFor="recipientAccNumber">Recipient:</label>
          <input
            type="text"
            id="recipientAccNumber"
            name="recipient"
            placeholder="Account Number"
            value={recipient}
            onChange={(e) => setRecipient(e.target.value)}
          />
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

export default Transfermoney;
