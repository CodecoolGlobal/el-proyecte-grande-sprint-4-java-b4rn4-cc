import "./App.css";
import About from "./components/About";

function App() {
  return (
    <div className="main">
      <div className="nav">
        <div className="bankName">
          <strong>El Grande Banco</strong>
        </div>
        <div className="navLinks">
          <div>Account Details</div>
          <div className="services">
            Services
            <div>Deposit</div>
            <div>Withdraw</div>
            <div>Transaction History</div>
            <div>Transer Money</div>
            <div>Pay Bills</div>
            <div>Loan</div>
          </div>
          <div id="about">About</div>
        </div>
      </div>
      <div className="content">
        <About />
      </div>
    </div>
  );
}

export default App;
