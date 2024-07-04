import React from "react";

import {Link, useNavigate} from "react-router-dom";

const Header = () => {
    const navigate = useNavigate(); // Hook 함수는 직접 사용 불가함

    function gotoLogin() {
        navigate("/login");
    }


    // 리턴은 화면을 그리겠다 (JSX)
    return (
        <div className="container header">
            <header
                className={"d-flex flex-wrap align-items-center justify-content-md-between py-3 mb-4 border-bottom"}>
                <div id="logo-area" className="col-md-1 mb-2">
                    <a href="/home" className="d-inline-flex link-body-emphasis text-decoration-none">
                        <img src={require('../img/logo2.jpeg')} alt="잠시 기달ㅋㅋ" width={200}/>
                    </a>
                </div>
                <ul className="nav col-12 col-md-6 justify-content-center">
                    <li><Link to="/home" className="nav-link px-2 link-secondary">HOME</Link></li>
                    <li><Link to="/boardList" className="nav-link px-2 link-secondary">BOARD</Link></li>
                    <li><Link to="/qnaList" className="nav-link px-2 link-secondary">Q&A</Link></li>
                </ul>

                <div className="col-md-3 text-end me-3">
                    <button type="button" className="btn btn-outline-warning me-2" onClick={gotoLogin}>LogIn</button>
                    <button type="button" className="btn btn-warning me-2">SignUp</button>
                </div>
            </header>
        </div>
    );
}

// 무조건 넣어야한다
export default Header;