import React from "react";
import { useNavigate } from "react-router-dom";
import "./HomeComponent.css";
import background from "../images/background.jpg";
import members from "../images/members.jpg";
import CardComponent from "./CardComponent";

function HomeComponent() {
  const des1 =
    "Do you want to check out ongoing heists? Or you want to create one of your own? No problem! Just click the button bellow ⬇";

  const des2 =
    "Do you want to check out existing members? Or you want to add new member? No problem! Just click the button bellow ⬇";

  return (
    <React.Fragment>
      <div>
        <br></br>
        <h1 className="text-center">Welcome to the Heist Planner</h1>
        <br></br>
        <div className="inline">
          <CardComponent
            linkName="Check Heists"
            cardText={des1}
            title="Heists"
            image={background}
            link="/heists"
          />
          <CardComponent
            image={members}
            cardText={des2}
            linkName="Check Members"
            title="Members"
            link="/members"
          />
        </div>
      </div>
    </React.Fragment>
  );
}

export default HomeComponent;
