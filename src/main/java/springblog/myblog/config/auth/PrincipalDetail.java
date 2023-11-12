package springblog.myblog.config.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springblog.myblog.entity.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 스프링 시큐리티로 로그인을 진행하고 완료되면 UserDetails 타입의 Object를
 * 스프링 시큐리티의 고유한 세션 저장소에 저장한다.
 */
@Data
public class PrincipalDetail implements UserDetails {

    private User user;

    public PrincipalDetail(User user){
        this.user = user;
    }

    // 계정의 권한을 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();

        collectors.add(() -> {
            return  "ROLE_" + user.getRole();
        });
        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
