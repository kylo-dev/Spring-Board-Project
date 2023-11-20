package springblog.myblog.controller.api;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springblog.myblog.domain.User;
import springblog.myblog.dto.ResponseDto;
import springblog.myblog.dto.user.JoinUserDto;
import springblog.myblog.dto.user.UserDto;
import springblog.myblog.service.UserService;
import springblog.myblog.service.jwt.JwtService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;


    // Vue 로그인 API
    @PostMapping("/api/user/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res){
        User user = userService.findByUsernameAndPassword(params.get("username"), params.get("password"));

        if(user != null) {
            String token = jwtService.getToken("id", user.getId());

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            res.addCookie(cookie);

            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // 회원가입 처리 API
    @PostMapping("/api/user/join")
    public ResponseDto<Integer> join(@RequestBody JoinUserDto userDto){
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .build();

        userService.save(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    // 특정 회원 조회
    @GetMapping("/api/user/{id}")
    public UserDto detail(@PathVariable Long id){
        User user = userService.findById(id);
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }

    // JWT 체크
    @GetMapping("/api/user/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token){
        Claims claims = jwtService.getClaims(token);

        if(claims != null) {
            Long id = Long.parseLong(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // 로그아웃 API
    @PostMapping("/api/user/logout")
    public ResponseDto<Integer> logout(@CookieValue(value = "token", required = false) String token,
                                       HttpServletResponse res){
        // 쿠키 삭제를 위해 새로운 쿠키를 생성하고 만료 일자를 이전 날짜로 설정
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);

        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

}
