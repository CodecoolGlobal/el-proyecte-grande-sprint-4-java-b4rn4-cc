import React, { useEffect, useState } from "react";
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

const Landing = () => {
  const [currencies, setCurrencies] = useState(initialCurrencyState);
  const [news, setNews] = useState(initialNewsState);

  useEffect(() => {
    const getCurrencies = async () => {
      const data = await apiGet("http://localhost:8080/main");

      setCurrencies(data);
    };
    getCurrencies();
  }, []);

  useEffect(() => {
    const getNews = async () => {
      const news = await apiGet("http://localhost:8080/news");
      setNews(news);
    };
    getNews();
  }, []);

  return (
    <>
      <h1>Welcome</h1>
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
    </>
  );
};

export default Landing;
