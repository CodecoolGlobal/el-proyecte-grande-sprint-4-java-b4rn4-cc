import React from "react";

const About = () => {
  return (
    <div>
      <h2>About us</h2>
      <div className={"CoDetails"}>
          <div className={"line"}>
              <p>Headquarters:</p>
              <p>Banco Grande Incorporated</p>
          </div>
          <div className={"line"}>
              <p>IBAN:</p>
              <p>HU49</p>
          </div>
          <div className={"line"}>
              <p>BIC:</p>
              <p>BANCGRANHUDF</p>
          </div>
          <div className={"line"}>
              <p>Location:</p>
              <p>1234 Budapest, Hungary, Hal Street 99.</p>
          </div>
          <div className={"line"}>
              <p>Contact (email):</p>
              <p>bancogrande.support@bqc.com</p>
          </div>
          <div className={"line"}>
              <p>Contact (phone):</p>
              <p>555-123-321</p>
          </div>
          <div className={"line"}>
              <p>Founded:</p>
              <p>1895. 02. 10.</p>
          </div>
          <div className={"line"}>
              <p>CEO:</p>
              <p>Janus Aurus</p>
          </div>
      </div>
      <div>@2022.02.21.</div>
    </div>
  );
};

export default About;
