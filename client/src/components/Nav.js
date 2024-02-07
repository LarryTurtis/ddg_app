/* eslint-disable import/no-anonymous-default-export */
import NavBar from "./NavBar";
import Home from "./Home";
import "../css/App.css";

export default (props) => (
  <div className="App">
    <header className="App-header">
      <NavBar />
    </header>
    <Home />
  </div>
);
