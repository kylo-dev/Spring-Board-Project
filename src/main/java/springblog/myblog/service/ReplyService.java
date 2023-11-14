package springblog.myblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springblog.myblog.domain.Board;
import springblog.myblog.domain.Reply;
import springblog.myblog.domain.User;
import springblog.myblog.dto.reply.SaveReplyDto;
import springblog.myblog.repository.BoardRepository;
import springblog.myblog.repository.ReplyRepository;
import springblog.myblog.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성 처리
    @Transactional
    public void createReply(SaveReplyDto replyDto){

        // 회원 조회
        User findUser = userRepository.findById(replyDto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("회원 찾기 실패 : 회원을 찾을 수 없습니다."));

        // 게시글 조회
        Board findBoard = boardRepository.findById(replyDto.getBoardId()).orElseThrow(() ->
                new IllegalArgumentException("게시글 찾기 실패 : 해당 글을 찾을 수 없습니다."));

        // 댓글 작성
        Reply reply = Reply.createReply(findUser, findBoard, replyDto.getContent());
        replyRepository.save(reply);
    }

    // 댓글 삭제 처리
    @Transactional
    public void deleteById(Long id){
        replyRepository.deleteById(id);
    }

}
