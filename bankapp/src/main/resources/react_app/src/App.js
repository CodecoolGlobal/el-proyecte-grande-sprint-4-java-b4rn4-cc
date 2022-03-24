import { useState, useEffect } from "react";
import "./App.css";
import {Routes, Route} from "react-router-dom";
import About from "./components/About";
import AccountDetails from "./components/AccountDetails";
import Deposit from "./components/Deposit";
import Loan from "./components/Loan";
import Paybills from "./components/Paybills";
import Transactionhistory from "./components/Transactionhistory";
import Transfermoney from "./components/Transfermoney";
import Withdraw from "./components/Withdraw";
import { apiGet } from "./FetchApis";
import Landing from "./components/Landing";
import Layout from "./components/Layout";
import {useLocation} from "react-router";

const initialUserState = {
  name: "",
  address: "",
  accountList: [],
};

function App() {
  const [details, setDetails] = useState(initialUserState);
  let userID = "11111111-2222-3333-4444-555555555555";
  const location = useLocation();

  useEffect(() => {
    const getDetails = async () => {
      const data = await apiGet(`/user/${userID}`);
      setDetails(data);
    };
    getDetails();
  }, [location.pathname]);

  return (
    <Routes>
      <Route path="/" element={<Layout/>}>
        <Route path="/" element={<Landing/>}/>
        <Route path="/account-details" element={<AccountDetails details={details}/>}/>
        <Route path="/deposit" element={<Deposit accounts={details.accountList}/>}/>
        <Route path="/withdraw" element={<Withdraw accounts={details.accountList}/>}/>
        <Route path="/history" element={<Transactionhistory accounts={details.accountList}/>}/>
        <Route path="/transfer" element={<Transfermoney accounts={details.accountList}/>}/>
        <Route path="/pay-bills" element={<Paybills userID={userID}/>}/>
        <Route path="/loan" element={<Loan/>}/>
        <Route path="/about" element={<About/>}/>
      </Route>
    </Routes>
);
}

export default App;
