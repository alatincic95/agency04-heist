import React, { useState, useEffect } from "react";
import "./CreateMember.css";
import CloseButton from "react-bootstrap/CloseButton";
import { useNavigate, useParams } from "react-router-dom";
import GetMemberService from "../services/GetMemberService";

function UpdateMember(props) {
  const navigate = useNavigate();
  const [member, setMember] = useState({});
  const [skills, setSkills] = useState([]);
  const [skill, setSkill] = useState({
    name: "",
    level: "",
  });

  let objectForSending = { skills: skills };
  console.log(objectForSending);

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
    });
  }, []);
  console.log(skills);

  const changeSkillHandler = (event) => {
    event.preventDefault();
  };

  const changeSkillNameHandler = (event) => {
    setSkill((prevState) => {
      return { ...prevState, name: event.target.value };
    });
  };

  const levelChangeHandler = (event) => {
    event.preventDefault();
    setSkill((prevState) => {
      return { ...prevState, level: event.target.value };
    });
  };

  const addSkillHandler = (event) => {
    event.preventDefault();
    setSkills([...skills, skill]);
    setSkill({ name: "", level: "" });
  };

  const removeSkillHandler = (event) => {
    let updatedSkills = [...skills];
    updatedSkills.splice(event, 1);
    console.log(updatedSkills);
    setSkills(updatedSkills);
  };

  let response;
  async function updateMemberHandler1() {
    response = await GetMemberService.updateMemberSkills1(
      params.memberId,
      objectForSending
    );
  }

  console.log(response);

  return (
    <React.Fragment>
      <h1 className="text-center">Update Heist Member</h1>
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
                      <div>
                        <div className="form-group">
                          <input
                            placeholder="Skill"
                            name="skill"
                            className="form-control"
                            value={skill.skillName}
                            onChange={changeSkillNameHandler}
                          ></input>

                          <div>
                            <div>Level</div>
                            <select
                              value={skill.skillLevel}
                              onChange={levelChangeHandler}
                            >
                              <option value="*">*</option>
                              <option value="**">**</option>
                              <option value="***">***</option>
                              <option value="****">****</option>
                              <option value="*****">*****</option>
                              <option value="******">******</option>
                              <option value="*******">*******</option>
                              <option value="********">********</option>
                              <option value="*********">*********</option>
                              <option value="**********">**********</option>
                            </select>
                            <br></br>
                          </div>
                        </div>
                      </div>
                      <div>
                        <button
                          onClick={addSkillHandler}
                          className="button-size btn btn-primary"
                        >
                          Add Skill
                        </button>
                        <div>
                          {skills.map((sk1, i) => {
                            return (
                              <div key={i} className="added-skill">
                                <span>{sk1.name}</span>
                                <span>{`(${sk1.level})`}</span>
                                <CloseButton
                                  onClick={() => removeSkillHandler(i)}
                                  className="btn btn-danger"
                                ></CloseButton>
                              </div>
                            );
                          })}
                        </div>
                      </div>
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
                          onClick={updateMemberHandler1}
                          className="btn button-size btn-success"
                        >
                          Submit
                        </button>
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

export default UpdateMember;
