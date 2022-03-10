import React, { useEffect, useState } from "react";
import { loadProp } from "./ReloadMemory";
import { apiGet } from "../FetchApis";
import Bill from "./Bill";

const Paybills = ({ userID }) => {
  let loadedUser = loadProp(userID, "userID", "");
  const [bills, setBills] = useState([]);
  const [accountNumber, setAccountNumber] = useState({});
  const [checkingAccounts, setCheckingAccounts] = useState([]);
  const [balance, setBalance] = useState("-");

  useEffect(() => {
    getBalance();
  }, [accountNumber]);

  function getBalance() {
    for (let acc of checkingAccounts) {
      if (acc.accountNumber === accountNumber) {
        setBalance(`${acc.balance} ${acc.currency}`);
      }
    }
  }

  useEffect(() => {
    localStorage.setItem("userID", JSON.stringify(loadedUser));
    const getBillsAndAccounts = async () => {
      const checkingAccountsResponse = await apiGet(
        "/account/user/" + loadedUser + "/checking"
      );
      const billsResponse = await apiGet(
        "/account/user/" + loadedUser + "/bills"
      );
      setBills(billsResponse);
      setCheckingAccounts(checkingAccountsResponse);
      setAccountNumber(checkingAccountsResponse[0].accountNumber);
    };

    getBillsAndAccounts();
  }, []);

  return (
    <div className="transaction">
      <h2>Bills</h2>
      <div className="accountNumberHistory">
        <div>Charged account:</div>
        <select
          style={{
            fontSize: "22px",
            padding: "10px",
            borderRadius: "15px",
            backgroundColor: "aliceblue",
          }}
          name="sender"
          id="sender"
          onChange={(event) => setAccountNumber(event.target.value)}
        >
          {checkingAccounts.map((acc) => (
            <option key={acc.accountNumber} value={acc.accountNumber}>
              {acc.accountNumber}
            </option>
          ))}
        </select>
      </div>
      <div className={"CoDetails"}>
        <div className={"line"}>
          <p>Balance:</p>
          <p>{balance}</p>
        </div>
      </div>
      <table className="historyTable">
        <thead>
          <tr>
            <td>Recipient</td>
            <td>Amount</td>
            <td>Status</td>
            <td> </td>
          </tr>
        </thead>
        {bills.map((bill, index) => (
          <Bill key={index} bill={bill} accountNumber={accountNumber} />
        ))}
      </table>
    </div>
  );
};

export default Paybills;
