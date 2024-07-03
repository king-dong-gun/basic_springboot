import {useParams} from "react-router-dom";
import {useState} from "react";

function BoardDetail() {
    const { bno } = useParams(); // useParams를 통해 bno 파라미터를 가져옴
    console.log(bno); // bno 값 출력

    // 여기서 bno를 이용해 데이터를 가져오는 로직을 추가할 수 있음

    return (
        <div className="container">
            <h1>{bno}번글에 들어온걸 환영ㅋㅋ</h1>
        </div>
    );
}

export default BoardDetail;
