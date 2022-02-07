import React, { useState, useEffect } from "react";
import "./CreateMember.css";
import CloseButton from "react-bootstrap/CloseButton";
import { useNavigate, useParams } from "react-router-dom";
import GetMemberService from "../services/GetMemberService";
import HeistService from "../services/HeistService";

function ViewHeist(props) {
  const navigate = useNavigate();
  const [heist, setHeist] = useState({});
  const [skills, setSkills] = useState([]);

  const closeFormHandler = () => {
    navigate(`/heists`);
  };

  const params = useParams();
  console.log(params.heistId);

  useEffect(() => {
    HeistService.getHeist(params.heistId).then((res) => {
      setHeist(res.data);
    });
  }, []);
  console.log(heist);

  useEffect(() => {
    HeistService.getHeist(params.heist).then((res) => {
      setSkills(res.data);
      console.log(res.data);
    });
  }, []);
  console.log(skills);

  return (
    <React.Fragment>
      <h1 className="text-center">View Heist</h1>
      <div className="container">
        <div className="row">
          <div className="card col-md-10 offset-md-1 offset-md-1">
            <div className="card-body">
              <form>
                <div className="form-group">
                  <div>Name: {heist.name}</div>
                </div>
                <div className="form-group">
                  <br></br>
                  <div>Location: {heist.location}</div>
                  <br></br>
                  <div>
                    <div className="form-group">
                      <div>Start Time: {heist.startTime}</div>
                      <br></br>
                    </div>
                    <div className="form-group">
                      <div>End Time: {heist.endTime}</div>
                      <br></br>
                    </div>
                    <div>
                      {skills.map((skill) => {
                        return (
                          <div>
                            <div>Skill name: {skill.skillName}</div>
                            <div>Skill level: {skill.skillLevel}</div>
                            <div>Members: {skill.members}</div>
                          </div>
                        );
                      })}
                    </div>
                    <div>
                      <div className="form-group"></div>
                      <div>
                        <div>Status: {heist.status}</div>
                      </div>
                      <br></br>
                      <div>
                        <button
                          onClick={closeFormHandler}
                          className="btn button-size btn-danger"
                        >
                          Cancel
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
}

export default ViewHeist;
