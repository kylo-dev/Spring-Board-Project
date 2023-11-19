package springblog.myblog.service;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springblog.myblog.domain.User;
import springblog.myblog.domain.enums.UserRole;
import springblog.myblog.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    @PersistenceContext
    EntityManager em;

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final AuthenticationManager authenticationManager;

    // 회원 저장
    @Transactional
    public void save(User user){
        validationDuplicateUser(user);
        user.setPassword(user.getPassword());
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }

    // 중복 회원 검사
    private void validationDuplicateUser(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if(optionalUser.isPresent()) {
            throw new IllegalStateException("중복 회원입니다. 다른 아이디를 사용해주세요");
        }
    }

    // 회원 pk로 조회
    public User findById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

//    @Transactional
//    public void update(User reqUser){
//        User user = userRepository.findById(reqUser.getId()).orElseThrow(() ->
//                new IllegalArgumentException("회원 정보를 수정할 수 없습니다."));
//
//        // oauth 필드에 값이 없어야 수정 가능 (= 카카오 로그인은 수정할 수 없음)
//        if (user.getOauth() == null || user.getOauth().equals("")){
//            user.setPassword(bCryptPasswordEncoder.encode(reqUser.getPassword()));
//            user.setEmail(reqUser.getEmail());
//        }
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(reqUser.getUsername(), reqUser.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    // 회원 pk로 삭제
    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    //== VUE API 관련 로직 ==//
    public User findByUsernameAndPassword(String username, String password){
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        return optionalUser.orElse(null);
    }


}
