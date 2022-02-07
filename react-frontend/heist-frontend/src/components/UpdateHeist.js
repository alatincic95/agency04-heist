import React, { useState, useEffect } from "react";
import "./CreateMember.css";
import CloseButton from "react-bootstrap/CloseButton";
import { useNavigate, useParams } from "react-router-dom";
import GetMemberService from "../services/GetMemberService";
import HeistService from "../services/HeistService";
import InputNumber from "react-input-number";

function UpdateHeist(props) {
  const navigate = useNavigate();
  const [heist, setHeist] = useState({});
  const [skills, setSkills] = useState([]);
  const [members, setMembers] = useState(1);
  const [skill, setSkill] = useState({
    name: "",
    level: "",
    members: "",
  });

  let objectForSending = { skills: skills };
  console.log(objectForSending);

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
    HeistService.getHeistSkills(params.heistId).then((res) => {
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
    setSkill({ name: "", level: "", members: "" });
  };

  const removeSkillHandler = (event) => {
    let updatedSkills = [...skills];
    updatedSkills.splice(event, 1);
    console.log(updatedSkills);
    setSkills(updatedSkills);
  };

  let response;
  async function updateHeistHandler1() {
    response = await HeistService.updateHeistSkills(
      params.memberId,
      objectForSending
    );
  }

  console.log(response);

  return (
    <React.Fragment>
      <h1 className="text-center">Update Heist </h1>
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
                      <div>End Time: {heist.endTime}</div>
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
                            <label>Members Count </label>
                            <div>
                              <InputNumber
                                min={1}
                                max={10}
                                step={1}
                                value={members}
                                onChange={setMembers}
                              />
                            </div>
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
                      <div>
                        <div>Status: {heist.status}</div>
                      </div>
                      <br></br>
                      <div>
                        <button
                          onClick={updateHeistHandler1}
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

export default UpdateHeist;
