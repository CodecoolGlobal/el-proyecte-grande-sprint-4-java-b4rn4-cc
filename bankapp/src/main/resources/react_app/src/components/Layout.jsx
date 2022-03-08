import {Outlet, useNavigate} from "react-router";
import React, {useState} from 'react';
import Header from "./Header";



const Layout = () => {
    const [renderEvent, setRenderEvent] = useState(0);
    const navigate = useNavigate();

    const handleClick = (page) => {
        navigate(page);
        setClickedDetails(s => !s);
        setRenderEvent(Date.now());
    }


    return (
        <>
            <Header renderEvent={renderEvent} handleClick={handleClick}/>
            <div className="main">
                <div className="nav">
                    <div className="bankName" onClick={() => handleClick('/')}>
                        <strong>El Grande Banco</strong>
                    </div>
                    <div className="navLinks">
                        <div onClick={() => handleClick('/account-details')}>
                            Account Details
                        </div>
                        <div className="service">
                            Services
                        </div>
                        <div className="serviceLinks">
                            <div onClick={() => handleClick("/deposit")}>Deposit</div>
                            <div onClick={() => handleClick("/withdraw")}>Withdraw</div>
                            <div onClick={() => handleClick("/deposit")}>Transaction History</div>
                            <div onClick={() => handleClick("/transfer")}>Transer Money</div>
                            <div onClick={() => handleClick("/pay-bills")}>Pay Bills</div>
                            <div onClick={() => handleClick("/loan")}>Loan</div>
                        </div>
                        <div id="about" onClick={() => handleClick("/about")}>About</div>
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
