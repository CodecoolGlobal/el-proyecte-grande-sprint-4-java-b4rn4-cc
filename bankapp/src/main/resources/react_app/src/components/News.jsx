import React from "react";

const News = ({ result }) => {
  const handleClick = () => {
    window.open(result.link);
  };

  return (
    <div className="single-news" onClick={(_event) => handleClick()}>
      <div id="news-title">{result.title}</div>
      <div id="news-date">{result.pubDate}</div>
      <div id="news-source">{result.source_id}</div>
    </div>
  );
};

export default News;
