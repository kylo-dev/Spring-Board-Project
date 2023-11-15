//package springblog.myblog.config.auth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import springblog.myblog.domain.User;
//import springblog.myblog.repository.UserRepository;
//
//@Service
//@RequiredArgsConstructor
//public class PrincipalDetailService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    /**
//     * 스프링이 로그인 요청을 가로챌 때, (username, password) 2개를 가로채는데
//     * password 부분 처리는 자동으로 처리 됨
//     * username만 DB에 있는지 확인해주면 됨
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User principal = userRepository.findByUsername(username)
//                .orElseThrow(()->{
//                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
//                });
//        return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장
//    }
//}
