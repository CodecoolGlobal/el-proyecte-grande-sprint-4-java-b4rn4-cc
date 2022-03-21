import React from "react";
import Transaction from "./Transaction";
import { apiGet } from "../FetchApis";
import { useEffect, useState } from "react";
import {loadProp} from "./ReloadMemory";

const Transactionhistory = ({ accounts }) => {
  let loadedAccounts = loadProp(accounts, 'accounts', "");
  const [transactions, setTransactions] = useState([]);
  const [accToView, setAccToView] = useState(loadedAccounts[0].accountNumber);

  useEffect(() => {
    localStorage.setItem('accounts', JSON.stringify(loadedAccounts));
  }, []);

  useEffect(() => {
    const getTransactions = async () => {
      const data = await apiGet("/account/" + accToView + "/history");
      setTransactions(data);
    };
    getTransactions();
  }, [accToView]);

  return (
    <div className="transaction">
      <h2>Account History</h2>
      <div className="accountNumberHistory">
        <div>Account number: </div>
        <select
          name="accNumber"
          id="accNumber"
          onChange={(event) => setAccToView(event.target.value)}
        >
          {loadedAccounts.map((acc) => (
            <option key={acc.accountNumber} value={acc.accountNumber}>
              {acc.accountNumber}
            </option>
          ))}
        </select>
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
