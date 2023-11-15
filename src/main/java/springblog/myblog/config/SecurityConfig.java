//package springblog.myblog.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import springblog.myblog.config.auth.PrincipalDetailService;
//
//@Configuration
//@EnableWebSecurity // Security Filter 등록
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근시 권한 및 인증 미리 체크
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final PrincipalDetailService principalDetailService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .cors(cors -> cors.disable())
//                .csrf().disable() // csrf 토큰 비활성화
//                .authorizeHttpRequests()
//                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/api/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/auth/loginForm") // 로그인 경로 설정
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로챔
//                .defaultSuccessUrl("/"); // 로그인 성공시 리다이렉션 경로 설정
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 시큐리티가 대신 로그인해주며, password를 가로챈다.
//     * 해당 password가 무엇으로 해쉬함수가 적용되었는지 알아야 함
//     * 같은 해쉬로 암호화해서 DB에 있는 값과 비교 후 로그인을 한다.
//     */
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(principalDetailService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//}
