import React, {useEffect, useState} from "react";
import {apiGet} from "../FetchApis";
import AccountsTable from "./AccountsTable";

const AccountDetails = () => {

    const [details, setDetails] = useState({});

    useEffect(() => {
        const getDetails = async () => {
            const data = await apiGet(
                "http://localhost:8080/user/11111111-2222-3333-4444-555555555555"
            );
            setDetails(data);
        };
        getDetails();
    }, []);
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
            <div className={"CoDetails"} style={{width: "80%"}}>
                <AccountsTable accounts={details.accountList}/>
            </div>
        </div>
    )
};


export default AccountDetails;
