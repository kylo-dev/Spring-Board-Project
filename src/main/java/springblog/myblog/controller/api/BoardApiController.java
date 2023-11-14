package springblog.myblog.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springblog.myblog.config.auth.PrincipalDetail;
import springblog.myblog.domain.Board;
import springblog.myblog.dto.ResponseDto;
import springblog.myblog.dto.board.SaveBoardDto;
import springblog.myblog.service.BoardService;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 게시판 생성
    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody SaveBoardDto boardDto, @AuthenticationPrincipal PrincipalDetail principal){
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .build();
        boardService.write(board, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    // 게시판 수정
    @PatchMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody SaveBoardDto boardDto){
        boardService.update(id, boardDto.getTitle(), boardDto.getContent());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    // 게시판 삭제
    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> delete(@PathVariable Long id){
        try{
            boardService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), 0);
        }
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
