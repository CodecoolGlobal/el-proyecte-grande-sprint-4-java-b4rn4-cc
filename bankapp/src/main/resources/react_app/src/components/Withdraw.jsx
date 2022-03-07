import React, {useState} from "react";

const Withdraw = ({ transferMoney, accounts }) => {
  const [amount, setAmount] = useState(0);
  const [sender, setSender] = useState(accounts[0]);
  const [currency, setCurrency] = useState(accounts[0].currency)
  const [message, setMessage] = useState("");
  const recipient = "00000000-0000-0000-0000-000000000000"

  const submit = (e) => {
    e.preventDefault();
    transferMoney({ amount, sender, recipient, message });
    setAmount(0);
    setMessage("");
  }

  function getCurrency(accNumber) {
    for(let acc of accounts) {
      if(acc.accountNumber === accNumber) {
        setCurrency(acc.currency)
      }
    }
  }

  return <div className="transfer-container">
    <h1>Withdraw</h1>
    <form className="transfer-form" onSubmit={submit}>
      <div>
        <label>Sender:</label>
        <select
            name="accNumber"
            id="accNumber"
            onChange={(event) => {setSender(event.target.value); getCurrency(event.target.value)}}
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

export default Withdraw;
