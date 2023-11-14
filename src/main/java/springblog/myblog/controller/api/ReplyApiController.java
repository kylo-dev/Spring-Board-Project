package springblog.myblog.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springblog.myblog.dto.ResponseDto;
import springblog.myblog.dto.reply.SaveReplyDto;
import springblog.myblog.service.BoardService;
import springblog.myblog.service.ReplyService;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    // 게시판에 댓글 작성
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> writeReply(@RequestBody SaveReplyDto replyDto){
        replyService.createReply(replyDto);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    // 게시판에 작성된 댓글 삭제
    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> deleteReply(@PathVariable("replyId") Long replyId){
        replyService.deleteById(replyId);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
