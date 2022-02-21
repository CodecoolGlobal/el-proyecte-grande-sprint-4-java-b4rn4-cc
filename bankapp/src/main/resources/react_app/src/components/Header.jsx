import React from 'react';

const Header = () => {
    return (
        <ul className={"header"}>
            <li className={"noHover"}><a>4:59</a></li>
            <li><a href="#log">Log out</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#home">Home</a></li>
        </ul>
    );
};

export default Header;
