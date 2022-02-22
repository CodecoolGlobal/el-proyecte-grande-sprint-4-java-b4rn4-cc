import React from "react";
import Transaction from "./Transaction";
import { apiGet } from "../FetchApis";
import { useEffect, useState } from "react";

const Transactionhistory = () => {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    const getTransactions = async () => {
      const data = await apiGet(
        "http://localhost:8080/account/history?accountNumber=1"
      );
      setTransactions(data);
    };
    getTransactions();
  }, []);

  return (
    <div className="transaction">
      <h2>Account History</h2>
      <div className="accountNumberHistory">
        <div>Account number: </div>
        <div>1</div>
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
