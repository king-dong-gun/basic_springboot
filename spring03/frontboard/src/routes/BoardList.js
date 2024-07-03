// Rest API의 호출 핵심 (없으면 아예 안됨!)
import axios from "axios";
// Hook 함수
import React, {useEffect, useState} from "react";
// Navigation
import {Link} from "react-router-dom";
// 공통함수 추가
import * as common from "../common/CommonFunc";

function Board() {  // 객체를 만드는 함수
    // 변수 선언: return | render() html, react 태그에서 반복할 때 사용된다.
    const [boardList, setBoardList] = useState([]); // 배열값을 받아 상태를 저장하기 때문에 []
    const [pageList, setPageList] = useState([]);   // 페이징을 위한 배열 데이터 []
    const [nextBlock, setNextBlock] = useState(0);  // 다음 블럭으로 가기위한 값
    const [prevBlock, setPrevBlock] = useState(0);  // 이전 블럭으로 가기위한 값
    const [lastPage, setLastPage] = useState(0);    // 마지막 블럭 번호

    // 함수 선언: 가장 중요!!!
    // 비동기 요청이 완료될까지 기다림
    const getBoardList = async (page) => {
        var pageString = (page == null) ? "page=0" : "page=" + page;

        // 백엔드 서버가 실행되지 않으면 예외발생 >> AXIOS ERROR
        try {
            const response = (await axios.get("http://localhost:8080/api/board/list/free?" + pageString));

            const resultCode = response.data.resultCode;
            // console.log(resultCode);            // Ok or Error

            if (resultCode == 'OK') {
                setBoardList(response.data.data);    // 상태를 boardList 데이터에 저장
                console.log(response.data.data);

                const paging = response.data.pagingDto;  // 페이징 정보
                // console.log(paging);

                // getBoradList()내에 있는 지역변수
                const {endPage, nextBlock, pageNo, prevBlock, startPage, totalListSize, totalPageNo} = paging;
                const tempPages = [];

                // console.log(totalListSize);
                // console.log(pageNo);
                for (let i = startPage; i <= endPage; i++) {
                    tempPages.push(i);  // [1,2,3,4...]
                }
                setPageList(tempPages);
                setNextBlock(nextBlock);
                setPrevBlock(prevBlock);
                setLastPage(endPage);

            } else {
                alert("니가 문제입니다. 알아서 고치세요.")
            }
        } catch (error) {
            // console.log(">>>>>>>>" + error);
            alert("서버가 연결되지 않았습니다. 컴퓨터를 바꾸세요.");
        }
    }

    function onPageClick(page) {
        console.log(page);
        getBoardList(page - 1); // 스프링에서 0으로 시작했기 때문에 -1
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
                        <td className="text-center">
                            <Link to={`/boardDetail/${board.bno}`}>{board.title}</Link> &nbsp;
                            {board.replyDtoList && board.replyDtoList.length > 0 && (
                                <span className="badge text-bg-success">{board.replyDtoList.length}
                                </span>
                            )}
                        </td>
                        <td>{board.writer}</td>
                        <td>{board.hit}</td>
                        <td>{common.formatDate(board.createDate)}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            {/*페이징 처리*/}
            <div className="d-flex justify-content-center">
                <nav aria-label="Page navigation">
                    <ul className="pagination">
                        <li className="page-item">
                            <button className="page-link" aria-label="Previous" onClick={() => onPageClick(1)}>
                                <span>⏮️</span>
                            </button>
                        </li>
                        <li className="page-item">
                            <button className="page-link" aria-label="Previous" onClick={() => onPageClick(prevBlock)}>
                                <span>⏪️</span>
                            </button>
                        </li>
                        {pageList.map((page, index) => (
                            <li className="page-item" key={index}>
                                <button className="page-link" onClick={() => onPageClick(page)}>
                                    {page}
                                </button>
                            </li>
                        ))}
                        <li className="page-item">
                            <button className="page-link" aria-label="Next" onClick={() => onPageClick(nextBlock)}>
                                <span>⏩️</span>
                            </button>
                        </li>
                        <li className="page-item">
                            <button className="page-link" aria-label="Next" onClick={() => onPageClick(lastPage)}>
                                <span>⏭️</span>
                            </button>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    );
}

export default Board;