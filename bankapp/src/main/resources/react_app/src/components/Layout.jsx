import {Outlet, useNavigate} from "react-router";
import React, {useState} from 'react';
import Header from "./Header";



const Layout = ({setClickedDetails}) => {
    const [renderEvent, setRenderEvent] = useState(0);
    const navigate = useNavigate();
    const handleclick = (page) => {
        navigate(page);
        setClickedDetails(s => !s);
        setRenderEvent(Date.now());
    }


    return (
        <>
            <Header renderEvent={renderEvent}/>
            <div className="main">
                <div className="nav">
                    <div className="bankName" onClick={() => handleclick('/')}>
                        <strong>El Grande Banco</strong>
                    </div>
                    <div className="navLinks">
                        <div onClick={() => handleclick('/account-details')}>
                            Account Details
                        </div>
                        <div className="service">
                            Services
                        </div>
                        <div className="serviceLinks">
                            <div onClick={() => handleclick("/deposit")}>Deposit</div>
                            <div onClick={() => handleclick("/withdraw")}>Withdraw</div>
                            <div onClick={() => handleclick("/deposit")}>Transaction History</div>
                            <div onClick={() => handleclick("/transfer")}>Transer Money</div>
                            <div onClick={() => handleclick("/pay-bills")}>Pay Bills</div>
                            <div onClick={() => handleclick("/loan")}>Loan</div>
                        </div>
                        <div id="about" onClick={() => handleclick("/about")}>About</div>
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
