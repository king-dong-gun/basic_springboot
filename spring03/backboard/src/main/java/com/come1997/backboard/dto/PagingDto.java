package com.come1997.backboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingDto {
    private int pageSize;       // 화면당 보여지는 게시글 최대 갯수
    private int totalPageNo;    // 총 페이지 수
    private long totalListSize; // 총 게시글 수

    private int pageNo;     // 현재 페이지 번호
    private int startPage;  // 시작 페이지 번호
    private int endPage;    // 마지막 페이지 번호

    private int startIndex; // 시작 인덱스 번호

    private int block;      // 현재 블럭
    private int totalBlock; // 총 블럭수
    private int prevBlock;  // 이전 블럭 페이지
    private int nextBlock;  // 다음 블럭 페이지

    public PagingDto(int blockSize, int pageNo, long totalListSize, int pageSize) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalListSize = totalListSize;

        // 총 페이지 수 계산
        this.totalPageNo = (int) Math.ceil(this.totalListSize * 1.0 / this.pageSize);

        // 총 블록 수 계산
        this.totalBlock = (int) Math.ceil(this.totalPageNo * 1.0 / blockSize);

        // 현재 블록 계산
        this.block = (int) Math.ceil((this.pageNo * 1.0) / blockSize);

        // 현재 블록 시작 페이지
        this.startPage = (this.block - 1) * blockSize + 1;

        // 현재 블록 마지막 페이지
        this.endPage = this.startPage + blockSize - 1;

        // 블록 마지막 페이지 검증 (한 블록이 10페이지가 안 넘으면 마지막 페이지를 최대 페이지수로 변경)
        if (this.endPage > this.totalPageNo) this.endPage = this.totalPageNo;

        // 이전 블록 (클릭 시, 이전 블록 마지막 페이지)
        this.prevBlock = (this.block - 1) * blockSize;

        // 이전 블록 검증
        if (this.prevBlock < 1) this.prevBlock = 1;

        // 다음 블록
        this.nextBlock = this.block * blockSize + 1;

        // 다음 블록 검증
        if (this.nextBlock > this.totalPageNo) this.nextBlock = this.totalPageNo;

        // 시작 인덱스 번호
        this.startIndex = (this.pageNo - 1) * this.pageSize;
    }
}
