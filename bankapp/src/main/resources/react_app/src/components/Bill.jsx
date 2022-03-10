import React from "react";
import {apiGet} from "../FetchApis";
import {Link} from "react-router-dom";

const Bill = ({ bill, accountNumber }) => {
    const transaction = bill.transaction;
    const submit = () => {
        apiGet(`/account/${accountNumber}/pay-bill/${bill.id}`).then()
    };

    const button = <Link to="/history" onClick={submit}>Pay Bill</Link>




    return (
        <>
            <tbody>
            <tr>
                <td>{bill.recipient.name}</td>
                <td>{transaction.amount} {transaction.currency}</td>
                <td
                    className={
                        bill.paid
                            ? "transferOK"
                            : "transferFail"
                    }
                >
                    {transaction.status === "SUCCESSFUL" ? "PAID" : "UNPAID"}
                </td>
                <td>
                    {bill.paid ? "" : button}
                </td>
            </tr>
            </tbody>
        </>
    );
};

export default Bill;
