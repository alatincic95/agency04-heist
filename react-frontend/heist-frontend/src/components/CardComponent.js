import React from "react";

function CardComponent(props) {
  return (
    <div className="card" style={{ width: "40%" }}>
      <img src={props.image} />
      <div className="card-body">
        <h5 className="card-title">{props.title}</h5>
        <p className="card-text">{props.cardText}</p>
        <a href={props.link} className="btn btn-dark">
          {props.linkName}
        </a>
      </div>
    </div>
  );
}

export default CardComponent;
