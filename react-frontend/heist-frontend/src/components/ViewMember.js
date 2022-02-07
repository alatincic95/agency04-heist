import React, { useState, useEffect } from "react";
import "./CreateMember.css";
import CloseButton from "react-bootstrap/CloseButton";
import { useNavigate, useParams } from "react-router-dom";
import GetMemberService from "../services/GetMemberService";

function ViewMember(props) {
  const navigate = useNavigate();
  const [member, setMember] = useState({});
  const [skills, setSkills] = useState([]);

  const closeFormHandler = () => {
    navigate(`/members`);
  };

  const params = useParams();
  console.log(params.memberId);

  useEffect(() => {
    GetMemberService.getMember(params.memberId).then((res) => {
      setMember(res.data);
    });
  }, []);
  console.log(member);

  useEffect(() => {
    GetMemberService.getMemberSkills(params.memberId).then((res) => {
      setSkills(res.data);
      console.log(res.data);
    });
  }, []);
  console.log(skills);

  return (
    <React.Fragment>
      <h1 className="text-center">View Heist Member</h1>
      <div className="container">
        <div className="row">
          <div className="card col-md-10 offset-md-1 offset-md-1">
            <div className="card-body">
              <form>
                <div className="form-group">
                  <div>Name: {member.name}</div>
                </div>
                <div className="form-group">
                  <br></br>
                  <div>Sex: {member.sex}</div>
                  <br></br>
                  <div>
                    <div className="form-group">
                      <div>Email: {member.email}</div>
                      <br></br>
                    </div>
                    <div>
                      {skills.map((skill) => {
                        return (
                          <div>
                            <div>Skill name: {skill.skillName}</div>
                            <div>Skill level: {skill.skillLevel}</div>
                          </div>
                        );
                      })}
                    </div>
                    <div>
                      <div className="form-group">
                        <div>Main Skill: {member.mainSkill}</div>
                      </div>
                      <div>
                        <div>Status: {member.status}</div>
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

export default ViewMember;
