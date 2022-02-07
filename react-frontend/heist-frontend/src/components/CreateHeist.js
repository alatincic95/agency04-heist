import React, { useState } from "react";
import "./CreateMember.css";
import InputNumber from "react-input-number";
import CloseButton from "react-bootstrap/CloseButton";
import { useNavigate } from "react-router-dom";
import DateTimePicker from "react-datetime-picker";
import HeistService from "../services/HeistService";

function CreateHeist() {
  const [name, setName] = useState("");
  const [location, setLocation] = useState("");
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [skill, setSkill] = useState({
    name: "",
    level: "",
    members: "",
  });
  const [membersCount, setMembersCount] = useState(1);
  const [skills, setSkills] = useState([]);
  const [status, setStatus] = useState("");
  const navigate = useNavigate();

  const changeNameHandler = (event) => {
    event.preventDefault();
    setName(event.target.value);
  };

  const changeLocationHandler = (event) => {
    event.preventDefault();
    setLocation(event.target.value);
  };

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

  const membersChangeHandler = (event) => {
    event.preventDefault();
    setSkill((prevState) => {
      return { ...prevState, membersCount: event.target.value };
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

  const statusChangeHandler = (event) => {
    event.preventDefault();
    setStatus(event.target.value);
  };

  const changeStartDateHandler = (event) => {
    setStartTime(event);
    console.log(event);
  };

  const changeEndDateHandler = (event) => {
    setEndTime(event);
    console.log(endTime.toISOString());
  };

  const closeFormHandler = () => {
    navigate("/");
  };

  let objectForSending = {
    name: name,
    location: location,
    startTime: startTime,
    endTime: endTime,
    skills: skills,
  };

  async function createHeistHandler(event) {
    event.preventDefault();
    navigate("/heists");
    await HeistService.createHeist(objectForSending);
  }

  return (
    <React.Fragment>
      <h1 className="text-center">Create New Heist</h1>
      <div className="container">
        <div className="row">
          <div className="card col-md-10 offset-md-1 offset-md-1">
            <div className="card-body">
              <form>
                <div className="form-group">
                  <input
                    placeholder="Name"
                    name="name"
                    className="form-control"
                    value={name}
                    onChange={changeNameHandler}
                  ></input>
                </div>
                <div className="form-group">
                  <br></br>
                  <div className="form-group">
                    <input
                      placeholder="Location"
                      name="location"
                      className="form-control"
                      value={location}
                      onChange={changeLocationHandler}
                    ></input>
                  </div>
                  <div>
                    <div>
                      <br></br>
                      <label>Start Time</label>
                      <br></br>
                      <DateTimePicker
                        value={startTime}
                        onChange={changeStartDateHandler}
                      ></DateTimePicker>
                      <br></br>
                      <label>End Time</label>
                      <br></br>
                      <DateTimePicker
                        value={endTime}
                        onChange={changeEndDateHandler}
                      ></DateTimePicker>
                      <br></br>
                    </div>
                    <div>
                      <br></br>
                      <div className="form-group">
                        <input
                          placeholder="Skill"
                          name="skill"
                          className="form-control"
                          value={skill.name}
                          onChange={changeSkillNameHandler}
                        ></input>

                        <div>
                          <div>Level</div>
                          <select
                            value={skill.level}
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
                        <div>
                          <label>Members Count </label>
                          <br></br>
                          <InputNumber
                            min={1}
                            max={10}
                            step={1}
                            value={membersCount}
                            onChange={membersChangeHandler}
                          />
                        </div>
                      </div>
                    </div>
                    <div>
                      <button
                        onClick={addSkillHandler}
                        className="button-size btn btn-dark"
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
                      <br></br>
                      <div>
                        <label>Status</label>
                        <br></br>
                        <select onChange={statusChangeHandler}>
                          <option value="PLANNING">PLANNING</option>
                        </select>
                      </div>
                      <br></br>
                      <div>
                        <button
                          onClick={createHeistHandler}
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

export default CreateHeist;
