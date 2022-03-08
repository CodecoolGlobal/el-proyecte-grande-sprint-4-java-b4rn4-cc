import {Outlet, useNavigate} from "react-router";
import React from 'react';
import Header from "./Header";



const Layout = () => {
    const navigate = useNavigate();

    return (
        <>
            <Header/>
            <div className="main">
                <div className="nav">
                    <div className="bankName">
                        <strong>El Grande Banco</strong>
                    </div>
                    <div className="navLinks">
                        <div onClick={() => navigate('/account-details')}>
                            Account Details
                        </div>
                        <div className="service">
                            Services
                        </div>
                        <div className="serviceLinks">
                            <div onClick={() => navigate("/deposit")}>Deposit</div>
                            <div onClick={() => navigate("/withdraw")}>Withdraw</div>
                            <div onClick={() => navigate("/deposit")}>Transaction History</div>
                            <div onClick={() => navigate("/transfer")}>Transer Money</div>
                            <div onClick={() => navigate("/pay-bills")}>Pay Bills</div>
                            <div onClick={() => navigate("/loan")}>Loan</div>
                        </div>
                        <div id="about" onClick={() => navigate("/about")}>About</div>
                    </div>
                </div>
                <div className="content">
                    <Outlet/>
                </div>
            </div>
        </>
    );
};

export default Layout;
