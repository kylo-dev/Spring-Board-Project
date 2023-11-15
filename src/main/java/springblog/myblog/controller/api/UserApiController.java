package springblog.myblog.controller.api;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springblog.myblog.domain.User;
import springblog.myblog.dto.ResponseDto;
import springblog.myblog.dto.user.JoinUserDto;
import springblog.myblog.repository.UserRepository;
import springblog.myblog.service.UserService;
import springblog.myblog.service.jwt.JwtService;
import springblog.myblog.service.jwt.JwtServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;

    // 회원가입 요청
//    @PostMapping("/auth/api/user")
//    public ResponseDto<Integer> save(@RequestBody JoinUserDto joinUserDto){
//        User user = User.builder()
//                .username(joinUserDto.getUsername())
//                .password(joinUserDto.getPassword())
//                .email(joinUserDto.getEmail())
//                .build();
//        userService.save(user);
//        return new ResponseDto<>(HttpStatus.OK.value(), 1);
//    }

    //== Vue 연동 테스트 ==//
    @GetMapping("/api/test")
    public List<String> getUsers(){
        List<String> items = new ArrayList<>();
        items.add("alpha");
        items.add("aple");

        return items;
    }

    // Vue 로그인 API
    @PostMapping("/api/user/login/test")
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

}
