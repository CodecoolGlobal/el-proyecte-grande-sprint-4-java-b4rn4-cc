import React from "react";
import Transaction from "./Transaction";

const Transactionhistory = ({ transactions }) => {
  return (
    <div className="transaction">
      <h2>Account History</h2>
      <div className="accountNumberHistory">
        <div>Account number: </div>
        <div>{transactions[0].sender}</div>
      </div>
      <table className="historyTable">
        <thead>
          <tr>
            <td>Date</td>
            <td>Recipient</td>
            <td>Amount</td>
            <td>Status</td>
          </tr>
        </thead>
        {transactions.map((transaction, index) => (
          <Transaction key={index} transaction={transaction} />
        ))}
      </table>
    </div>
  );
};

export default Transactionhistory;
