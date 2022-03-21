import React from "react";
import { apiGet } from "../FetchApis";
import { Link } from "react-router-dom";

const Bill = ({ bill, accountNumber }) => {
  const transaction = bill.transaction;
  const submit = () => {
    apiGet(`/account/${accountNumber}/pay-bill/${bill.id}`).then();
  };

  const button = (
    <button
      style={{
        backgroundColor: "#b4c7dc",
        height: "40px",
        borderRadius: "10px",
      }}
    >
      <Link
        style={{ textDecoration: "none", color: "black", fontSize: "18px" }}
        to="/history"
        onClick={submit}
      >
        Pay Bill
      </Link>
    </button>
  );

  return (
    <>
      <tbody>
        <tr>
          <td>{bill.recipient.name}</td>
          <td>
            {transaction.amount} {transaction.currency}
          </td>
          <td className={bill.paid ? "transferOK" : "transferFail"}>
            {transaction.status === "SUCCESSFUL" ? "PAID" : "UNPAID"}
          </td>
          <td>{bill.paid ? "" : button}</td>
        </tr>
      </tbody>
    </>
  );
};

export default Bill;
