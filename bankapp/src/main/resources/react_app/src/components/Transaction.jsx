import React from "react";

const Transaction = ({ transaction }) => {
  return (
    <>
      <tbody>
        <tr>
          <td>{new Date(transaction.transactionTime).toUTCString()}</td>
          <td>{transaction.recipient.accountNumber}</td>
          <td>{transaction.amount} {transaction.currency}</td>
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
