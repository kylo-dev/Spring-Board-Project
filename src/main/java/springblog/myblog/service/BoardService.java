package springblog.myblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springblog.myblog.domain.Board;
import springblog.myblog.domain.User;
import springblog.myblog.repository.BoardRepository;
import springblog.myblog.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 글쓰기 - 회원 정보 매핑
    @Transactional
    public Long write(Board board, User user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(() ->
                new IllegalArgumentException("글 작성 실패 : 작성자 ID를 찾을 수 없습니다."));

        board.setUser(findUser);
        board.setCount(0);
        Board saveBoard = boardRepository.save(board);
        return saveBoard.getId();
    }

    // 페이징 글 조회
    public Page<Board> findAll(Pageable pageable){
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage;
    }

//    public List<Board> findAllBoardWithUser(){
//        return boardRepository.
//    }

    // 게시글 조회
    public Board findById(Long id){
        return boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("글 찾기 실패 : 게시글 ID를 찾을 수 없습니다."));
    }

    // 게시글과 회원 정보 모두 조회
    public Board findBoardWithUser(Long id){
        return boardRepository.findBoardWithUser(id).orElseThrow(()->
                new IllegalArgumentException("글 찾기 실패 : 게시글 ID를 찾을 수 없습니다."));
    }

    // 게시글 수정
    @Transactional
    @Modifying(clearAutomatically = true)
    public void update(Long id, String title, String content) {
        Board board = boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("글 찾기 실패 : 게시글을 찾을 수 없습니다.")); //영속화
        board.setTitle(title);
        board.setContent(content);
    }

    // 게시글 삭제
    @Transactional
    public void deleteById(Long id){
        boardRepository.deleteById(id);
    }
}
