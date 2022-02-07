import React, { useState, useEffect, Component } from "react";
import { useNavigate } from "react-router-dom";
import GetMemberService from "../services/GetMemberService";
import MemberService from "../services/MemberService";
import bin from "../images/bin.png";
import update from "../images/update.png";
import view from "../images/view.svg";

import classes from "./ListComponent.module.css";

const ListMembers = (props) => {
  const [members, setMembers] = useState([]);
  const [member, setMember] = useState();

  useEffect(() => {
    MemberService.getMembers().then((res) => {
      setMembers(res.data);
    });
  }, []);
  const navigate = useNavigate();

  const addMember = () => {
    navigate("/create-member");
  };
  const viewMemberHandler = (memberId) => {
    navigate(`/view-member/${memberId}`);
  };
  const updateMemberHandlerNav = (memberId) => {
    navigate(`/update-member/${memberId}`);
  };

  function deleteMemberHandlerNav(memberId, i) {
    members.splice(i, 1);
    setMembers(members);
    navigate("/members");
    GetMemberService.deleteMember(memberId);
  }

  console.log(member);

  return (
    <div>
      <h2 className={`text-center  ${classes.top}`}>All Members</h2>
      <div>
        <button className="btn btn-dark add" onClick={addMember}>
          Add Member
        </button>
      </div>
      <div className="row">
        <table className="table table-striped table bordered ">
          <thead>
            <tr>
              <th>Member name</th>
              <th>Member email</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {members.map((member, i) => {
              return (
                <tr key={member.id}>
                  <td>{member.name}</td>
                  <td>{member.email}</td>
                  <button
                    title="View"
                    onClick={() => viewMemberHandler(member.id)}
                    className={`btn ${classes.btn3}`}
                  >
                    {<img src={view} style={{ height: 25, width: 25 }}></img>}
                  </button>
                  <button
                    title="Update"
                    onClick={() => updateMemberHandlerNav(member.id)}
                    className={`btn  ${(classes.btnsize, classes.btn1)}`}
                  >
                    {<img src={update} style={{ height: 25, width: 25 }}></img>}
                  </button>
                  <button
                    title="Delete"
                    onClick={() => deleteMemberHandlerNav(member.id, i)}
                    className={`btn  ${(classes.btnsize, classes.btn2)}`}
                  >
                    {<img src={bin} style={{ height: 25, width: 25 }}></img>}
                  </button>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListMembers;
