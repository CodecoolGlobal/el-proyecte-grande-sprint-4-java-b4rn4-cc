import React, {useState} from "react";

const Withdraw = ({ transferMoney }) => {
  const [amount, setAmount] = useState(0);
  const [sender, setSender] = useState("cd601a0a-ff98-47fc-90a9-d5fe1b5fa26c");
  const [message, setMessage] = useState("");
  const recipient = "00000000-0000-0000-0000-000000000000"

  const submit = (e) => {
    e.preventDefault();
    transferMoney({ amount, sender, recipient, message });
    setAmount(0);
    setMessage("");
  }

  return <div className="transfer-container">
    <h1>Withdraw</h1>
    <form className="transfer-form" onSubmit={submit}>
      <div>
        <label htmlFor="recipientAccNumber">Sender:</label>
        <input
            type="text"
            id="recipientAccNumber"
            name="recipient"
            placeholder="Account Number"
            value={sender}
            onChange={(e) => setSender(e.target.value)}
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
  </div>;
};

export default Withdraw;
