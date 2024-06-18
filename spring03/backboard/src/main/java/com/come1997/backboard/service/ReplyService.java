package com.come1997.backboard.service;


import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Reply;
import com.come1997.backboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    // 메서드 작성
    // 댓글 작성 기능
    public void setReply(Board board, String content) {
        // 빌더를 사용하는 방식
        Reply reply = Reply.builder().content(content).createDate(LocalDateTime.now()).board(board).build();
        this.replyRepository.save(reply);
        // 전통적인 방식
        // Reply reply1 = new Reply();
        // reply1.setBoard(board);

    }
}
