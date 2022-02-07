import React from "react";
import { Link } from "react-router-dom";
import "./Header.css";
import { useNavigate } from "react-router-dom";

const Header = (props) => {
  const navigate = useNavigate();

  const goToMembers = () => {
    navigate("/members");
  };
  return (
    <div>
      <header>
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
          <div>
            <Link to="/home" className="navbar-brand left">
              Heist Planner
            </Link>
            <Link to="/members" className="navbar-brand left">
              Members
            </Link>
            <Link to="/heists" className="navbar-brand left">
              Heists
            </Link>
          </div>
        </nav>
      </header>
    </div>
  );
};

export default Header;
