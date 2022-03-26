import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { apiGet } from "../FetchApis";
import "../Main.css";
import News from "./News";

const initialCurrencyState = {
  base: null,
  rates: {
    GBP: 1,
    JPY: 2,
    USD: 3,
    HUF: 350,
  },
};

const initialNewsState = {
  status: "fail",
  results: [
    {
      title: "1st news",
      link: "",
      source_id: "newsRoom",
    },
  ],
};

const Landing = ({ setLoggedIn, setUserId }) => {
  const [currencies, setCurrencies] = useState(initialCurrencyState);
  const [news, setNews] = useState(initialNewsState);
  const [isLoading, setIsLoading] = useState(false);

  let { username } = useParams();

  useEffect(() => {
    const getUser = async () => {
      if (username) {
        const user = await apiGet("/user?username=" + username);
        setUserId(user.userID);
        setLoggedIn(true);
      }
    };
    getUser();
  }, [username]);

  useEffect(() => {
    const getCurrencies = async () => {
      const data = await apiGet("/account/main");

      setCurrencies(data);
    };
    getCurrencies();
  }, []);

  useEffect(() => {
    const getNews = async () => {
      let news;
      setIsLoading(true);
      news = await apiGet("/news");
      setNews(news);
      setIsLoading(false);
    };
    getNews();
  }, []);

  return (
    <>
      <h1>Welcome {username}</h1>
      {isLoading ? (
        <div id="loading" className="display"></div>
      ) : (
        <div>
          <div className="news-container">
            {news.results.map((result, index) => (
              <News key={index} result={result} />
            ))}
          </div>

          <div className="cur-container">
            <div className="currency">
              <div>1 {currencies.base}</div>
              <div className="national-cur">GBP</div>
              <div>{currencies.rates.GBP.toFixed(2)}</div>
            </div>
            <div className="currency">
              <div>1 {currencies.base}</div>
              <div className="national-cur">JPY</div>
              <div>{currencies.rates.JPY.toFixed(2)}</div>
            </div>
            <div className="currency">
              <div>1 {currencies.base}</div>
              <div className="national-cur">USD</div>
              <div>{currencies.rates.USD.toFixed(2)}</div>
            </div>
            <div className="currency">
              <div>1 {currencies.base}</div>
              <div className="national-cur">HUF</div>
              <div>{currencies.rates.HUF.toFixed(2)}</div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Landing;
