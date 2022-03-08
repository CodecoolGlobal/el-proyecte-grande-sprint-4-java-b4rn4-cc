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
            {accounts && accounts.length > 0 && accounts.map((account) => (
                <tr key={account.accountNumber}>
                    <td>{account.accountNumber}</td>
                    <td>{account.type}</td>
                    <td>{account.balance}</td>
                    <td>{account.currency}</td>
                    <td><Link to="/deposit" state={{accNum:account.accountNumber}}>Deposit</Link><br/>
                        <Link to="/withdraw" state={{accNum:account.accountNumber, currency:account.currency}}>Withdraw</Link>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default AccountsTable;
