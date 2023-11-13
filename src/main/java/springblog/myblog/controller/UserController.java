package springblog.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springblog.myblog.config.auth.PrincipalDetail;
import springblog.myblog.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Value("${cos.key}")
    private String cosKey;

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth//loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal, Model model){
        model.addAttribute("principal", principal);
        return "user/updateForm";
    }
}
