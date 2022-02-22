import { useState, useEffect } from "react";
import "./App.css";
import About from "./components/About";
import Header from "./components/Header";
import Accountdetails from "./components/Accountdetails";
import Deposit from "./components/Deposit";
import Loan from "./components/Loan";
import Paybills from "./components/Paybills";
import Transactionhistory from "./components/Transactionhistory";
import Transfermoney from "./components/Transfermoney";
import Withdraw from "./components/Withdraw";
import {apiGet} from "./FetchApis";

function App() {
    const [renderThis, setRenderThis] = useState();
    const [transactions, setTransactions] = useState([]);

    const [renderEvent, setRenderEvent] = useState(false)
    useEffect(() => {
        const getData = async () => {
            const data = await apiGet(
                "http://localhost:8080/account/history?accountNumber=1"
            );
            setTransactions(data);
        };
        getData();
    }, []);

    useEffect(() => {
        if (renderEvent) {
            setRenderEvent(false)
        } else {
            setRenderEvent(true);
        }
    }, []);

    const handleClick = (e) => {
        const element = e.target;
        setRenderThis(element.innerText);
    };

    return (
        <div>
            <Header renderEvent={renderEvent}/>
            <div className="main">
                <div className="nav">
                    <div className="bankName">
                        <strong>El Grande Banco</strong>
                    </div>
                    <div className="navLinks">
                        <div onClick={(event) => handleClick(event)} id="accDetails">
                            Account Details
                        </div>
                        <div className="service" id="service">
                            Services
                        </div>
                        <div className="serviceLinks">
                            <div onClick={(event) => handleClick(event)}>Deposit</div>
                            <div onClick={(event) => handleClick(event)}>Withdraw</div>
                            <div onClick={(event) => handleClick(event)}>
                                Transaction History
                            </div>
                            <div onClick={(event) => handleClick(event)}>Transer Money</div>
                            <div onClick={(event) => handleClick(event)}>Pay Bills</div>
                            <div onClick={(event) => handleClick(event)}>Loan</div>
                        </div>
                        <div id="about" onClick={(event) => handleClick(event)}>
                            About
                        </div>
                    </div>
                </div>
                <div className="content">
                    {renderThis === "About" && <About/>}
                    {renderThis === "Deposit" && <Deposit/>}
                    {renderThis === "Withdraw" && <Withdraw/>}
                    {renderThis === "Transer Money" && <Transfermoney/>}
                    {renderThis === "Pay Bills" && <Paybills/>}
                    {renderThis === "Loan" && <Loan/>}
                    {renderThis === "Transaction History" && <Transactionhistory transactions={transactions}/>}
                    {renderThis === "Account Details" && <Accountdetails/>}
                </div>
            </div>
        </div>
    );
}

export default App;
