import React, {useState} from "react";

const Deposit = ({ transferMoney }) => {
  const [amount, setAmount] = useState(0);
  const [recipient, setRecipient] = useState("");
  const [message, setMessage] = useState("");
  const sender = "00000000-0000-0000-0000-000000000000";
  const currency = "EUR";

  const submit = (e) => {
    e.preventDefault();
    transferMoney({ amount, currency, sender, recipient, message });
    setAmount(0);
    setMessage("");
    setRecipient("");
  }

  return <div className="transfer-container">
    <h1>Deposit</h1>
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
