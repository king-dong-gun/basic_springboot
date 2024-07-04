import React, {useState} from "react";
import {Link} from "react-router-dom";
// Rest Api
import axios from "axios";

function Login() {
    // 상태 변수 선언
    const [user, setUser] = useState({
        id: "",
        password: "",
    });

    // 함수 선언
    // 입력 필드의 변화가 있을 때 호출
    const handleChange = (e) => {
        const {name, value} = e.target; // id 또는 password 중 하나
        setUser({...user, [name]: value});
    }
    // 폼이 제출될 때 호출
    const handleSubmit = async (e) => {
        e.preventDefault(); // 폼 제출 방지

        try {
            // 객체를 생성하고, 사용자 ID와 비밀번호를 추가
            const formData = new FormData();
            formData.append("id", user.id);
            formData.append("password", user.password);

            console.log(formData.get('id') + "/" + formData.get('password'));

            // axios 백엔드 호출하기
            const response = await axios({
                url: "http://localhost:8080/api/member/login",
                method: "post", // get, post, delete, put...
                data: formData,
                widthCredentials: true,
            });
            console.log(response);

            if (response.data.resultCode == "OK") {
                alert("어서오고ㅋㅋ");
            } else {
                alert("좀 똑바로 보고 치세요.")
            }

        } catch (error) {
            console.log("로그인 에러 >>>>>>>>>>>>>>> " + error);
            alert("좀 똑바로 보고 치세요.")
        }

    }
    return (
        <div className="container card shadow-sm"
             style={{maxWidth: "400px, padding: 1rem"}}>
            <div className="d-flex justify-content-center">
                <div>
                    <h3 className="text-center">LogIn</h3>
                    <form onSubmit={handleSubmit}>
                        <div className="text-start mb-3">
                            <lable htmlFor="id" className="form-label">ID</lable>
                            <input type="text" name="id" className="form-control" placeholder="니 아이디 적으셈ㅋㅋ"
                                   required value={user.id} onChange={handleChange}/>
                        </div>
                        <div className="text-start  mb-3">
                            <lable password="password" className="form-label">Password</lable>
                            <input type="password" name="password" className="form-control" placeholder="니 비번 적으셈ㅋㅋ"
                                   required value={user.password} onChange={handleChange}/>
                        </div>
                        <button type="submit" className="btn btn-primary my-3 me-2">들오셈ㅋㅋ</button>
                        <Link to={"/home"} className="btn btn-secondary btn-block">나가셈ㅋㅋ</Link>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Login;