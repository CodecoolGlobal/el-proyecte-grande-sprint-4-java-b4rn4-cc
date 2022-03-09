import React from 'react';
import {Link} from "react-router-dom";

const AccountsTable = ({accounts}) => {

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
            {accounts && accounts.length > 0 && accounts.map((acc) => (
                <tr key={acc.accountNumber}>
                    <td>{acc.accountNumber}</td>
                    <td>{acc.type}</td>
                    <td>{acc.balance}</td>
                    <td>{acc.currency}</td>
                    <td><Link to="/deposit" state={{accountNumber:acc.accountNumber}}>Deposit</Link><br/>
                        <Link to="/withdraw" state={{accountNumber:acc.accountNumber, currency:acc.currency}}>Withdraw</Link>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default AccountsTable;
