import React, { useState } from "react";
import "./CreateMember.css";
import CloseButton from "react-bootstrap/CloseButton";
import { useNavigate } from "react-router-dom";
import GetMemberService from "../services/GetMemberService";

function CreateMember() {
  const [name, setName] = useState("");
  const [sex, setSex] = useState("");
  const [email, setEmail] = useState("");
  const [mainSkill, setMainSkill] = useState("");
  const [status, setStatus] = useState("");
  const [skill, setSkill] = useState({
    name: "",
    level: "",
  });
  const [skills, setSkills] = useState([]);
  const navigate = useNavigate();

  const changeNameHandler = (event) => {
    event.preventDefault();
    setName(event.target.value);
  };

  const changeSexHandler = (event) => {
    setSex(event.target.value);
  };

  const changeEmailHandler = (event) => {
    event.preventDefault();
    setEmail(event.target.value);
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

  const mainSkillChangeHandler = (event) => {
    console.log(event);
    setMainSkill(event.target.value);
    console.log(event.target.value);
  };

  const statusChangeHandler = (event) => {
    event.preventDefault();
    setStatus(event.target.value);
  };

  const closeFormHandler = () => {
    navigate("/");
  };

  let objectForSending = {
    name: name,
    sex: sex,
    email: email,
    skils: skills,
    mainSkill: mainSkill,
    status: status,
  };

  async function createMember1(event) {
    event.preventDefault();
    navigate("/");
    await GetMemberService.createMember1(objectForSending);
  }

  return (
    <React.Fragment>
      <h1 className="text-center">Create New Heist Member</h1>
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
                  <label>Sex</label>
                  <div>
                    <div>
                      <input
                        type="radio"
                        value="M"
                        name="sex"
                        checked={sex === "M"}
                        onChange={changeSexHandler}
                      />{" "}
                      Male
                    </div>
                    <div>
                      <input
                        type="radio"
                        value="F"
                        name="sex"
                        checked={sex === "F"}
                        onChange={changeSexHandler}
                      />{" "}
                      Female
                    </div>
                  </div>
                  <div>
                    <div className="form-group">
                      <input
                        placeholder="Email"
                        name="email"
                        className="form-control"
                        value={email}
                        onChange={changeEmailHandler}
                      ></input>
                      <br></br>
                    </div>
                    <div>
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
                      <div className="form-group">
                        <label>Main Skill</label>
                        <br></br>
                        <select
                          value={mainSkill}
                          onChange={mainSkillChangeHandler}
                        >
                          {skills.map((sk2, i) => {
                            return (
                              <option key={i} value={sk2.name}>
                                {sk2.name}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                      <div>
                        <label>Status</label>
                        <br></br>
                        <select onChange={statusChangeHandler}>
                          <option value="AVAILABLE">AVAILABLE</option>
                          <option value="RETIRED">RETIRED</option>
                          <option value="INCARCERATED">INCARCERATED</option>
                          <option value="EXPIRED">EXPIRED</option>
                        </select>
                      </div>
                      <br></br>
                      <div>
                        <button
                          onClick={createMember1}
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

export default CreateMember;
