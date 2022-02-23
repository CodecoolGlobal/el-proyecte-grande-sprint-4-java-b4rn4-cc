import React from 'react';

const AccountsTable = ({accounts}) => {
    return (
        <table className="historyTable">
            <thead>
            <tr>
                <td>Account Number</td>
                <td>Balance</td>
                <td>Deposit</td>
            </tr>
            </thead>
            <tbody>
            {accounts && accounts.length > 0 && accounts.map((account) => (
                <tr key={account.accountNumber}>
                    <td>{account.accountNumber}</td>
                    <td>{account.balance}</td>
                    <td><button>Deposit</button></td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default AccountsTable;
