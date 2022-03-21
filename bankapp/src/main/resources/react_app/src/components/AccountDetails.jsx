import React from "react";
import AccountsTable from "./AccountsTable";

const AccountDetails = ({ details }) => {
  return (
    <div>
      <h2>User Details</h2>
      <div className={"CoDetails"}>
        <div className={"line"}>
          <p>User-ID:</p>
          <p>{details.userID}</p>
        </div>
        <div className={"line"}>
          <p>Name:</p>
          <p>{details.name}</p>
        </div>
        <div className={"line"}>
          <p>Address:</p>
          <p>{details.address}</p>
        </div>
      </div>
      <div className={"CoDetails"}>
        <AccountsTable accounts={details.accountList}/>
      </div>
    </div>
  );
};

export default AccountDetails;
