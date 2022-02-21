import "./App.css";
import About from "./components/About";
import Header from "./components/Header";

function App() {
  return (
      <div>
          <Header />
    <div className="main">
      <div className="nav">navbar</div>
      <div className="content">
        <About />
      </div>
    </div>
      </div>
  );
}

export default App;
