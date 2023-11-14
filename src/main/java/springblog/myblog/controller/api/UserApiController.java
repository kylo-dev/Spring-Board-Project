package springblog.myblog.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springblog.myblog.domain.User;
import springblog.myblog.dto.ResponseDto;
import springblog.myblog.dto.user.JoinUserDto;
import springblog.myblog.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 회원가입 요청
    @PostMapping("/auth/api/user")
    public ResponseDto<Integer> save(@RequestBody JoinUserDto joinUserDto){
        User user = User.builder()
                .username(joinUserDto.getUsername())
                .password(joinUserDto.getPassword())
                .email(joinUserDto.getEmail())
                .build();
        userService.save(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

}
