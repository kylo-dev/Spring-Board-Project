package springblog.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springblog.myblog.config.auth.PrincipalDetail;
import springblog.myblog.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model,
                       @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC)Pageable pageable){
        model.addAttribute("boards", boardService.findAll(pageable));
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String boardById(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDetail principal){
        model.addAttribute("board", boardService.findBoardWithUser(id));
        model.addAttribute("principal", principal);
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model){
        model.addAttribute("board", boardService.findById(id));
        return "/board/updateForm";
    }
}
