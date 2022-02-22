import React from "react";

const Transaction = ({ transaction }) => {
  return (
    <>
      <tbody>
        <tr>
          <td>{new Date(transaction.transactionTime).toUTCString()}</td>
          <td>{transaction.recipient}</td>
          <td>{transaction.amount}</td>
          <td
            className={
              transaction.status === "SUCCESSFUL"
                ? "transferOK"
                : "transferFail"
            }
          >
            {transaction.status}
          </td>
        </tr>
      </tbody>
    </>
  );
};

export default Transaction;
