package springblog.myblog.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinUserDto {

    private String username;
    private String password;
    private String email;
}
