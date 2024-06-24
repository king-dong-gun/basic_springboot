package com.come1997.backboard.service;


import com.come1997.backboard.common.NotFoundException;
import com.come1997.backboard.entity.Board;
import com.come1997.backboard.entity.Member;
import com.come1997.backboard.entity.Reply;
import com.come1997.backboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    // 메서드 작성
    // 댓글 작성 기능
    public Reply setReply(Board board, String content, Member writer) {
        // 빌더를 사용하는 방식
        Reply reply = Reply.builder().content(content).createDate(LocalDateTime.now()).board(board).build();
        reply.setWriter(writer);    // 작성자 추가
        this.replyRepository.save(reply);

        return reply;
    }

    // 댓글을 수정하기 위한 댓글 가져오기
    public Reply getReply(Long rno) {
        Optional<Reply> reply = this.replyRepository.findById(rno);
        if (reply.isPresent())
            return reply.get();
        else
            throw new NotFoundException("Reply Not Found...!");
    }

    // 댓글 수정처리
    public void modReply(Reply reply, String content) {
        reply.setContent(content);
        reply.setModifyDate(LocalDateTime.now());

        this.replyRepository.save(reply);
    }

    // 댓글삭제
    public void remReply(Reply reply) {
        this.replyRepository.delete(reply);
    }
}
