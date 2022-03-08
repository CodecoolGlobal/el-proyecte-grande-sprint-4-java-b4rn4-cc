import React, {useEffect, useState} from 'react';

const Header = ({handleclick, renderEvent}) => {
    const [time, setTimeLeft] = useState(120);

    useEffect(() => {
        if (time > 0) {
            const timer = setTimeout(()=> {
                setTimeLeft(x => x-1);
            }, 1000);
            return ()=> clearTimeout(timer);
        }
    }, [time]);

    useEffect(() => {
        setTimeLeft(300)
    }, [renderEvent]);


    function secondsToTime(time) {
        let divisor_for_minutes = time % (60*60);
        let minutes = Math.floor(divisor_for_minutes / 60);

        let divisor_for_seconds = divisor_for_minutes % 60;
        let seconds = Math.ceil(divisor_for_seconds);
        if(seconds < 10) {
            return `${minutes}:0${seconds}`;
        }
        return `${minutes}:${seconds}`;
    }


    return (
        <ul className={"header"}>
            <li className={"noHover"}><a>{secondsToTime(time)} </a></li>
            <li><a  onClick={() => {handleclick("/")}}>Log out</a></li>
            <li><a onClick={() => {handleclick("/")}}>Home</a></li>
        </ul>
    );
};

export default Header;
