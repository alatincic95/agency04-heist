import logo from "./logo.svg";
import "./App.css";
import ListMembers from "./components/ListMembersComponent";
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import CreateMember from "./components/CreateMember";
import ListHeistsComponent from "./components/ListHeistsComponent";
import CreateHeist from "./components/CreateHeist";
import HomeComponent from "./components/HomeComponent";
import ViewMember from "./components/ViewMember";
import UpdateMember from "./components/UpdateMember";
import ViewHeist from "./components/ViewHeist";
import UpdateHeist from "./components/UpdateHeist";

function App() {
  return (
    <React.Fragment>
      <Router>
        <Header />
        <div className="container">
          <Routes>
            <Route path="/" element={<ListMembers />} />
            <Route path="/home" element={<HomeComponent />} />
            <Route path="/members" element={<ListMembers />} />
            <Route path="/create-member" element={<CreateMember />} />
            <Route path="/heists" element={<ListHeistsComponent />} />
            <Route path="/create-heist" element={<CreateHeist />} />
            <Route path={`/view-member/:memberId`} element={<ViewMember />} />
            <Route path={`/view-heist/:heistId`} element={<ViewHeist />} />
            <Route
              path={`/update-member/:memberId`}
              element={<UpdateMember />}
            />
            <Route path={`/update-heist/:heistId`} element={<UpdateHeist />} />
          </Routes>
        </div>
        <Footer />
      </Router>
    </React.Fragment>
  );
}

export default App;
