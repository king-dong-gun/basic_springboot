package com.come1997.backboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Header<T> {
    private LocalDateTime transactionTime;  // json으로 전달한 시간
    private String resultCode;              // 트랜잭션이 성공인지 실패인지
    private String description;
    private T data;  // 실제 데이터 담는곳
    private PagingDto pagingDto;

    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK() {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("No Error")
                .build();
    }
    // Data를 받아서 전달
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(T data) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("No Error")
                .data(data)
                .build();
    }
    // Data와 페이징을 받아서 전달 (핵심 메서드)
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(T data, PagingDto pagingDto) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("No Error")
                .data(data)
                .pagingDto(pagingDto)
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(String description) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("Error")
                .description(description)
                .build();
    }
}
