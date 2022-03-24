import React from "react";
import Transaction from "./Transaction";
import { apiGet } from "../FetchApis";
import { useEffect, useState } from "react";
import {loadProp} from "./ReloadMemory";
import {useLocation} from "react-router-dom";

const Transactionhistory = ({ accounts }) => {
  const preset = useLocation().state;
  const selected = loadProp(preset, 'selected', {accountNumber: ""});
  const [transactions, setTransactions] = useState([]);
  const [accToView, setAccToView] = useState("");

  useEffect(() => {
    if(accounts !== undefined && accounts.length !== 0) {
      setAccToView(selected.accountNumber)
    }
  }, [accounts]);

  useEffect(() => {
    const getTransactions = async () => {
      const data = await apiGet("/account/" + accToView + "/history");
      setTransactions(data);
    };
    if(accToView !== "") {
      localStorage.setItem('selected', JSON.stringify({accountNumber: accToView}));
      getTransactions();
    }
  }, [accToView]);

  return (
    <div className="transaction">
      <h2>Account History</h2>
      <div className="accountNumberHistory">
        <div>Account number: </div>
        <select
          name="accNumber"
          id="accNumber"
          value={accToView ? String(accToView) : ""}
          onChange={(event) => setAccToView(event.target.value)}
        >
          {accounts.map((acc) => (
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
