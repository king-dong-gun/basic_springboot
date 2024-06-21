package com.come1997.backboard.service;


import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Member;
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
    public void setReply(Board board, String content, Member writer) {
        // 빌더를 사용하는 방식
        Reply reply = Reply.builder().content(content).createDate(LocalDateTime.now()).board(board).build();
        reply.setWriter(writer);    // 작성자 추가
        this.replyRepository.save(reply);
    }
}
