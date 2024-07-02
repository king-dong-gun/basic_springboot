// Rest API의 호출 핵심 (없으면 아예 안됨!)
import axios from "axios";
// Hook 함수
import React, {useEffect, useState} from "react";
// Navigation
import {Link, useNavigate} from "react-router-dom";


function Board() {  // 객체를 만드는 함수
    // 변수 선언
    const [boardList, setBoardList] = useState([]); // 배열값을 받아 상태를 저장하기 때문에 []

    // 함수 선언: 가장 중요!!!
    // 비동기 요청이 완료될까지 기다림
    const getBoardList = async () => {
        var pageString = 'page=0';
        const response = (await axios.get("http://localhost:8080/api/board/list/free?" + pageString)).data;
        setBoardList(response);    // 상태를 boardList 데이터에 저장
        console.log(boardList);
    }

    useEffect(() => {
        getBoardList();
    }, [])

    return (
        <div className="container">
            <table className="table">
                <thead className="table-dark">
                <tr className="text-center">
                    <th>No</th>
                    <th style={{width: '50%'}}>Title</th>
                    <th>Author</th>
                    <th>Views</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {/*반복으로 들어갈 부분*/}
                {boardList.map((board) => (
                    <tr className='text-center' key={board.bno}>
                        <td>{board.bno}</td>
                        <td className="text-center">{board.title}</td>
                        <td>{board.writer}</td>
                        <td>{board.hit}</td>
                        <td>{board.createDate}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default Board;