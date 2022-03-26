import React, {useEffect, useState} from "react";
import {apiGet} from "../FetchApis";

const Header = ({handleClick, renderEvent, setUserId}) => {
    const [time, setTimeLeft] = useState(120);

    const logout = async () => {
        await apiGet("/logout");
        setUserId(false);
    };

    useEffect(() => {
        if (time > 0) {
            const timer = setTimeout(() => {
                setTimeLeft((x) => x - 1);
            }, 1000);
            return () => clearTimeout(timer);
        }
    }, [time]);

    useEffect(() => {
        setTimeLeft(120);
    }, [renderEvent]);

    function secondsToTime(time) {
        let divisor_for_minutes = time % (60 * 60);
        let minutes = Math.floor(divisor_for_minutes / 60);

        let divisor_for_seconds = divisor_for_minutes % 60;
        let seconds = Math.ceil(divisor_for_seconds);
        if (seconds < 10) {
            return `${minutes}:0${seconds}`;
        }
        return `${minutes}:${seconds}`;
    }

    return (
        <ul className={"header"}>
            <li className={"noHover"}>
                <button>{secondsToTime(time)} </button>
            </li>
            <li>
                <button onClick={() => handleClick("/login")}>Log in</button>
            </li>
            <li>
                <button
                    onClick={() => {
                        logout();
                        handleClick("/");
                    }}
                >
                    Log out
                </button>
            </li>
            <li>
                <button
                    onClick={() => {
                        handleClick("/");
                    }}
                >
                    Home
                </button>
            </li>
        </ul>
    );
};

export default Header;
