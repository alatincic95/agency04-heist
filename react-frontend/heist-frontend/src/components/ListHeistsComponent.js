import React, { useState, useEffect } from "react";
import HeistService from "../services/HeistService";
import classes from "./ListComponent.module.css";
import { useNavigate } from "react-router-dom";
import bin from "../images/bin.png";
import update from "../images/update.png";
import view from "../images/view.svg";

const ListHeistsComponent = (props) => {
  const [heists, setHeists] = useState([]);

  useEffect(() => {
    HeistService.getHeists().then((res) => {
      setHeists(res.data);
    });
  }, []);
  console.log(heists);
  const navigate = useNavigate();

  const addHeist = () => {
    navigate("/create-heist");
  };

  const viewHeistHandler = (heistId) => {
    navigate(`/view-heist/${heistId}`);
  };

  const updateHeistHandlerNav = (heistId) => {
    navigate(`/update-heist/${heistId}`);
  };

  function deleteHeistHandlerNav(heistId, i) {
    heists.splice(i, 1);
    setHeists(heists);
    navigate("/heists");
    HeistService.deleteHeist(heistId);
  }

  return (
    <div>
      <h2 className={`text-center  ${classes.top}`}>All Heists</h2>
      <div>
        <button className="btn btn-dark" onClick={addHeist}>
          Add Heist
        </button>
      </div>
      <div className="row">
        <table className="table table-striped table bordered ">
          <thead>
            <tr>
              <th>Heist name</th>
              <th>Heist location</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {heists.map((heist, i) => {
              return (
                <tr key={heist.id}>
                  <td>{heist.name}</td>
                  <td>{heist.location}</td>
                  <button
                    title="View"
                    onClick={() => viewHeistHandler(heist.id)}
                    className={`btn ${classes.btn3}`}
                  >
                    {<img src={view} style={{ height: 25, width: 25 }}></img>}
                  </button>
                  <button
                    title="Update"
                    onClick={() => updateHeistHandlerNav(heist.id)}
                    className={`btn  ${(classes.btnsize, classes.btn1)}`}
                  >
                    {<img src={update} style={{ height: 25, width: 25 }}></img>}
                  </button>
                  <button
                    title="Delete"
                    onClick={() => deleteHeistHandlerNav(heist.id, i)}
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

export default ListHeistsComponent;
