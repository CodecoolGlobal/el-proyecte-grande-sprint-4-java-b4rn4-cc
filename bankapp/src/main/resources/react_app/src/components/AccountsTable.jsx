import React from "react";
import { Link } from "react-router-dom";

const AccountsTable = ({ accounts }) => {
  return (
    <table className="historyTable">
      <thead>
        <tr>
          <td>Account Number</td>
          <td>Account Type</td>
          <td>Balance</td>
          <td>Currency</td>
        </tr>
      </thead>
      <tbody>
        {accounts &&
          accounts.length > 0 &&
          accounts.map((acc) => (
            <tr key={acc.accountNumber}>
              <td>{acc.accountNumber}</td>
              <td>{acc.type}</td>
              <td>{acc.balance}</td>
              <td>{acc.currency}</td>
              <td>
                <button
                  style={{
                    backgroundColor: "#b4c7dc",
                    height: "40px",
                    marginBottom: "5px",
                    borderRadius: "10px",
                  }}
                >
                  <Link
                    style={{
                      textDecoration: "none",
                      color: "black",
                      fontSize: "18px",
                    }}
                    to="/deposit"
                    state={{
                        accountNumber: acc.accountNumber,
                        currency: acc.currency,
                    }}
                  >
                    Deposit
                  </Link>
                </button>
                <button
                  style={{
                    backgroundColor: "#b4c7dc",
                    height: "40px",
                    marginBottom: "5px",
                    borderRadius: "10px",
                  }}
                >
                  <Link
                    style={{
                      textDecoration: "none",
                      color: "black",
                      fontSize: "18px",
                    }}
                    to="/withdraw"
                    state={{
                      accountNumber: acc.accountNumber,
                      currency: acc.currency,
                    }}
                  >
                    Withdraw
                  </Link>
                </button>
              </td>
            </tr>
          ))}
      </tbody>
    </table>
  );
};

export default AccountsTable;
